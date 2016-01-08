package algorithm;

import entities.AbstractEntity;
import entities.CordNode;
import entities.Distances;
import repositories.DocumentRepository;
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
    @Inject
    private DocumentRepository documentRepository;
    Map<Long, DijkstraNode> dijkstraNodes;
    ArrayList<CordNode> cordNodes;

    private Dijkstra dijkstra;

    public Algorithm() throws IOException {
        dijkstraNodes = new HashMap<>(40000);
        cordNodes = new ArrayList<>(40000);

    }


    public ResponseList find(String startLat, String startLon, String endLat, String endLon, double threshhold) throws IOException {
        long startTime = System.currentTimeMillis();
        cordNodes.clear();
        cordNodes.addAll(documentRepository.getAll("CordNode").stream().map(ab -> (CordNode) ab).collect(Collectors.toList()));

        dijkstra = new Dijkstra();
        Long start = find(startLat, startLon);
        Long end = find(endLat, endLon);

        List<CordNode> path = findPath(start, end, threshhold);
        Integer distance = dijkstra.getDistance(dijkstraNodes, end);
        long endTime = System.currentTimeMillis();
        ResponseList rl = new ResponseList();
        rl.setEntities(path);
        rl.setTime(endTime - startTime);
        rl.setHits(path.size());
        rl.setDistance(distance);
        return rl;
    }

    private Long find(String lat, String lot) {
        return getClosest(lat, lot);
    }

    private List<CordNode> findPath(Long start, Long end, double threshhold) throws IOException {
        prepareDikstraNodes();
        dijkstra.setThreshold(threshhold);
        dijkstra.find(dijkstraNodes, start);

        List<CordNode> path = createPath(dijkstra.showPath(dijkstraNodes, end));

        return path;
    }

    private List<CordNode> createPath(ArrayList<Long> pathIds) {
        List<CordNode> path = new ArrayList<>(pathIds.size());
        List<CordNode> path_db=documentRepository.findIds(pathIds);
        Map<Long, CordNode> map = path_db.stream().collect(Collectors.toMap(CordNode::getCid ,item -> item));
        for (int i = pathIds.size() - 1; i >= 0; i--) {

            CordNode tn = map.get(pathIds.get(i));
            path.add(tn);
        }
        return path;
    }

    private void prepareDikstraNodes() throws IOException {
        dijkstraNodes.clear();
        List<AbstractEntity> entities = documentRepository.getAll("Distances");
        for (AbstractEntity entity : entities) {
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

    private Long getClosest(String _Lat, String _Lon) {
        double lat = Double.parseDouble(_Lat);
        double lon = Double.parseDouble(_Lon);

        CordNode baseNode = new CordNode(1L, lat, lon);

        double min_odl = cordNodes.get(0).minus(baseNode);
        CordNode closest = cordNodes.get(0);
        for (CordNode cordNode : cordNodes) {
            double temp_odl = cordNode.minus(baseNode);
            if (min_odl > temp_odl) {
                closest = cordNode;
                min_odl = temp_odl;
            }
        }
        System.out.println(min_odl + "--");
        return closest.getCid();
    }


}
