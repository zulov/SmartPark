package algorithm;

import entities.*;
import repositories.DocumentRepository;
import request.FindRequest;
import response.FindListResponse;
import response.Status;

import javax.ejb.Singleton;

import javax.inject.Inject;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Tomek on 2015-12-14.
 */
@Singleton
public class Algorithm {
    private final DistanceUtils distanceUtils = new DistanceUtils();
    private final float threshold = 4000.0f;
    @Inject
    private DocumentRepository documentRepository;

    private ArrayList<CordNode> cordNodes;
    private List<AbstractEntity> distances;
    private List<AbstractEntity> modifiers;
    private Map<Long, Float> mapModifiers;
    private List<AbstractEntity> parkings;
    private Map<Long, Parking> mapParkings;


    public FindListResponse find(FindRequest request) throws IOException {
        long startTime = System.currentTimeMillis();
        initDataFromDB();

        Dijkstra dijkstra = new Dijkstra();
        Long start = distanceUtils.getClosest(request.getLat(), request.getLon(), cordNodes, 1000);
        Long end = distanceUtils.getClosest(request.getEndLat(), request.getEndLon(), cordNodes, 1000);

        Map<Long, DijkstraNode> dijkstraNodes = calculate(dijkstra, start, threshold);
        List<CordNode> path = createPath(dijkstra.showPath(dijkstraNodes, end));
        Integer distance = dijkstra.getDistance(dijkstraNodes, end);

        FindListResponse rl = new FindListResponse("", Status.SUCCESS, System.currentTimeMillis() - startTime, path.size(), distance, path);
        return rl;
    }

    public FindListResponse findParking(FindRequest request) throws IOException {
        long startTime = System.currentTimeMillis();
        initDataFromDB();

        Dijkstra dijkstra = new Dijkstra();
        Long start = distanceUtils.getClosest(request.getLat(), request.getLon(), cordNodes, 250);
        if (start < 0) {
            FindListResponse rl = new FindListResponse("Poza obszarem!", Status.ERROR, System.currentTimeMillis() - startTime, 0, -1, null);
            return rl;
        }

        Map<Long, DijkstraNode> dijkstraNodes = calculate(dijkstra, start, threshold);
        List<AbstractEntity> userParkingDatas = documentRepository.getList("UserParkingData", "user", request.getUserId().toString());
        Long end = dijkstra.findBestParking(cordNodes, mapParkings, dijkstraNodes, userParkingDatas);

        ArrayList<Long> list = dijkstra.showPath(dijkstraNodes, end);
        List<CordNode> path = createPath(dijkstra, list);

        Integer distance = dijkstra.getDistance(dijkstraNodes, end);
        FindListResponse rl = new FindListResponse("", Status.SUCCESS, System.currentTimeMillis() - startTime, path.size(), distance, path);

        return rl;
    }

    private List<CordNode> createPath(Dijkstra dijkstra, ArrayList<Long> list) {
        List<CordNode> path = createPath(list);
        if (!path.isEmpty()) {
            path.add(dijkstra.bestParking.getLocalization());
        }
        return path;
    }

    private void initDataFromDB() {
        if (cordNodes == null) {
            cordNodes = new ArrayList<>(40000);
            cordNodes.addAll(documentRepository.getAll("CordNode").stream().map(ab -> (CordNode) ab).collect(Collectors.toList()));
        }
        if (distances == null) {
            distances = documentRepository.getAll("Distances");
        }
        if (parkings == null) {
            parkings = documentRepository.getAll("Parking");
            mapParkings = new HashMap<>(200);
            for (AbstractEntity ab : parkings) {
                Parking parking = (Parking) ab;
                mapParkings.put(parking.getLocalization().getCid(), parking);
            }
        }
        if (modifiers == null) {
            modifiers = documentRepository.getAll("TrafficData");
            mapModifiers = new HashMap<>(100000);
            for (AbstractEntity ab : modifiers) {
                TrafficData traffic = (TrafficData) ab;
                mapModifiers.put(traffic.getStartNode().getCid(), traffic.getBusyFactor());
            }
        }

    }

    private Map<Long, DijkstraNode> calculate(Dijkstra dijkstra, Long start, double threshold) throws IOException {
        Map<Long, DijkstraNode> dijkstraNodes = prepareDikstraNodes();
        dijkstra.setThreshold(threshold);
        dijkstra.calculate(dijkstraNodes, start);
        return dijkstraNodes;
    }

    private List<CordNode> createPath(ArrayList<Long> pathIds) {
        if (pathIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<CordNode> path = new ArrayList<>(pathIds.size());
        List<CordNode> path_db = documentRepository.findIds(pathIds);
        Map<Long, CordNode> map = path_db.stream().collect(Collectors.toMap(CordNode::getCid, item -> item));
        for (int i = pathIds.size() - 1; i >= 0; i--) {
            CordNode tn = map.get(pathIds.get(i));
            path.add(tn);
        }
        return path;
    }

    private Map<Long, DijkstraNode> prepareDikstraNodes() {
        Map<Long, DijkstraNode> dijkstraNodes = new HashMap<>(100000);
        List<Distances> modifyDistances = modifyDistances(distances, mapModifiers);
        for (Distances distances : modifyDistances) {
            if (!dijkstraNodes.containsKey(distances.getStartNode().getCid())) {
                dijkstraNodes.put(distances.getStartNode().getCid(), new DijkstraNode(0, distances.getStartNode().getCid()));
            }
            if (!dijkstraNodes.containsKey(distances.getEndNode().getCid())) {
                dijkstraNodes.put(distances.getEndNode().getCid(), new DijkstraNode(0, distances.getEndNode().getCid()));
            }
            DijkstraNode oldDijkstraNode = dijkstraNodes.get(distances.getStartNode().getCid());
            oldDijkstraNode.getNeightbours().add(new DijkstraNode(distances.getDistance(), distances.getEndNode().getCid()));
        }
        return dijkstraNodes;
    }

    private List<Distances> modifyDistances(List<AbstractEntity> abstractEntities, Map<Long, Float> _modifiers) {
        List<Distances> distances = new ArrayList<>(abstractEntities.size());
        for (AbstractEntity ab : abstractEntities) {
            Distances distance = (Distances) ab;

            distances.add(new Distances(distance.getStartNode(), distance.getEndNode(),
                    (int) (distance.getDistance() * _modifiers.get(distance.getStartNode().getCid()).floatValue())));
        }
        return distances;
    }

}
