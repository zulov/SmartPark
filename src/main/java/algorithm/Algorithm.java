package algorithm;

import entities.AbstractEntity;
import entities.CordNode;
import entities.Distances;
import repositories.DocumentRepository;
import request.Request;
import response.ResponseList;

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
    @Inject
    private DocumentRepository documentRepository;
    private Map<Long, DijkstraNode> dijkstraNodes;
    private ArrayList<CordNode> cordNodes;
    private List<AbstractEntity> distances;
    private List<AbstractEntity> parkings;
    private List<AbstractEntity> userParkingDatas;
    private Dijkstra dijkstra;

    public Algorithm() {
        dijkstraNodes = new HashMap<>(40000);
    }


    public ResponseList find(Request request) throws IOException {
        long startTime = System.currentTimeMillis();
        initDataFromDB();

        dijkstra = new Dijkstra();
        Long start = distanceUtils.getClosest(request.getStartLat(), request.getStartLon(), cordNodes);
        Long end = distanceUtils.getClosest(request.getEndLat(), request.getEndLon(), cordNodes);

        calculate(start, request.getThreshold());
        List<CordNode> path = createPath(dijkstra.showPath(dijkstraNodes, end));
        Integer distance = dijkstra.getDistance(dijkstraNodes, end);
        long endTime = System.currentTimeMillis();
        ResponseList rl = new ResponseList();
        rl.setEntities(path);
        rl.setTime(endTime - startTime);
        rl.setHits(path.size());
        rl.setDistance(distance);
        return rl;
    }

    public ResponseList findParking(Request request) throws IOException {
        long startTime = System.currentTimeMillis();
        initDataFromDB();

        dijkstra = new Dijkstra();
        Long start = distanceUtils.getClosest(request.getStartLat(), request.getStartLon(), cordNodes);

        calculate(start, request.getThreshold());
        Long end = dijkstra.findBestParking(cordNodes, parkings, dijkstraNodes);
        List<CordNode> path = createPath(dijkstra.showPath(dijkstraNodes, end));
        Integer distance = dijkstra.getDistance(dijkstraNodes, end);
        long endTime = System.currentTimeMillis();
        ResponseList rl = new ResponseList();
        rl.setEntities(path);
        rl.setTime(endTime - startTime);
        rl.setHits(path.size());
        rl.setDistance(distance);
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
        }
        userParkingDatas = documentRepository.getAll("UserParkingData");
    }

    private void calculate(Long start, double threshold) throws IOException {
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
