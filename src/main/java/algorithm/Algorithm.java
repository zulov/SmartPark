package algorithm;

import entities.AbstractEntity;
import entities.CordNode;
import entities.Distances;
import entities.Parking;
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
    private Map<Long, DijkstraNode> dijkstraNodes;
    private ArrayList<CordNode> cordNodes;
    private List<AbstractEntity> distances;
    private List<AbstractEntity> parkings;
    private Map<Long, Parking> mapParkings;
    private List<AbstractEntity> userParkingDatas;


    public Algorithm() {
        dijkstraNodes = new HashMap<>(100000);
    }


    public FindListResponse find(FindRequest request) throws IOException {
        long startTime = System.currentTimeMillis();
        initDataFromDB();

        Dijkstra dijkstra = new Dijkstra();
        Long start = distanceUtils.getClosest(request.getLat(), request.getLon(), cordNodes, 1000);
        Long end = distanceUtils.getClosest(request.getEndLat(), request.getEndLon(), cordNodes, 1000);

        calculate(dijkstra, start, threshold);
        List<CordNode> path = createPath(dijkstra.showPath(dijkstraNodes, end));
        Integer distance = dijkstra.getDistance(dijkstraNodes, end);

        FindListResponse rl = new FindListResponse("", Status.SUCCESS, System.currentTimeMillis() - startTime, path.size(), distance, path);
        return rl;
    }

    public FindListResponse findParking(FindRequest request) throws IOException {
        long startTime = System.currentTimeMillis();

        initDataFromDB();
        String userId = request.getUserId().toString();
        userParkingDatas = documentRepository.getList("UserParkingData", "user", userId);
        Dijkstra dijkstra = new Dijkstra();
        Long start = distanceUtils.getClosest(request.getLat(), request.getLon(), cordNodes, 250);
        if (start < 0) {
            FindListResponse rl = new FindListResponse("Poza obszarem!", Status.ERROR, System.currentTimeMillis() - startTime, 0, -1, null);
            return rl;
        }
        calculate(dijkstra, start, 4000.0);

        Long end = dijkstra.findBestParking(cordNodes, mapParkings, dijkstraNodes, userParkingDatas);

        ArrayList<Long> list = dijkstra.showPath(dijkstraNodes, end);
        List<CordNode> path = createPath(list);
        if (!path.isEmpty()) {
            path.add(dijkstra.bestParking.getLocalization());
        }

        Integer distance = dijkstra.getDistance(dijkstraNodes, end);
        FindListResponse rl = new FindListResponse("", Status.SUCCESS, System.currentTimeMillis() - startTime, path.size(), distance, path);

        return rl;
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
            mapParkings=new HashMap<>(200);
            for(AbstractEntity ab:parkings){
                Parking parking=(Parking) ab;
                mapParkings.put(parking.getLocalization().getCid(),parking);
            }
        }

    }

    private void calculate(Dijkstra dijkstra, Long start, double threshold) throws IOException {
        prepareDikstraNodes();
        dijkstra.setThreshold(threshold);
        dijkstra.calculate(dijkstraNodes, start);
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

    private void prepareDikstraNodes() {
        dijkstraNodes.clear();

        for (AbstractEntity entity : distances) {
            Distances distances = (Distances) entity;
            if (!dijkstraNodes.containsKey(distances.getStartNode().getCid())) {
                dijkstraNodes.put(distances.getStartNode().getCid(), new DijkstraNode(0, distances.getStartNode().getCid()));
            }
            if (!dijkstraNodes.containsKey(distances.getEndNode().getCid())) {
                dijkstraNodes.put(distances.getEndNode().getCid(), new DijkstraNode(0, distances.getEndNode().getCid()));
            }
            DijkstraNode oldDijkstraNode = dijkstraNodes.get(distances.getStartNode().getCid());
            oldDijkstraNode.getNeightbours().add(new DijkstraNode(distances.getDistance(), distances.getEndNode().getCid()));
        }
    }

}
