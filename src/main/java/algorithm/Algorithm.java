package algorithm;

import entities.AbstractEntity;
import entities.CordNode;
import entities.Distances;
import entities.Way;
import org.codehaus.jackson.map.ObjectMapper;
import repositories.DocumentRepository;
import response.ResponseList;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Tomek on 2015-12-14.
 */
@Stateless
//@LocalBean
//@Singleton
public class Algorithm {
    @Inject
    private DocumentRepository documentRepository;
    Map<Integer, DijkstraNode> dijkstraNodes;
    ArrayList<CordNode> cordNodes;

    private Dijkstra dijkstra;

    public Algorithm() throws IOException {
        dijkstraNodes = new HashMap<>(10000);
        cordNodes = new ArrayList<>();

        dijkstra = new Dijkstra();
    }

    public ResponseList find(String startLat, String startLon, String endLat, String endLon) throws IOException {
        long startTime = System.currentTimeMillis();
        cordNodes.addAll(documentRepository.getAll("CordNode").stream().map(ab -> (CordNode) ab).collect(Collectors.toList()));

        int start = find(startLat, startLon);
        int end = find(endLat, endLon);

        ArrayList<CordNode> path = findPath(start, end);
        long endTime = System.currentTimeMillis();
        ResponseList rl = new ResponseList();
        rl.setEntities(path);
        rl.setTime(endTime - startTime);
        rl.setHits(path.size());
        //rl.setDistance();
        return rl;
    }

    private int find(String lat, String lot) {
        return getClosest(lat, lot);
    }

    private ArrayList<CordNode> findPath(int start, int end) throws IOException {
        prepareDikstraNodes();
        dijkstra.setDocumentRepository(documentRepository);
        dijkstra.find(dijkstraNodes, start);

        ArrayList<CordNode> path = dijkstra.showPath(dijkstraNodes, end);
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

    private int getClosest(String _Lat, String _Lon) {
        double lat = Double.parseDouble(_Lat);
        double lon = Double.parseDouble(_Lon);

        CordNode baseNode = new CordNode(1, lat, lon);

        double min_odl = cordNodes.get(0).minus(baseNode);
        int min_id = cordNodes.get(0).getCid();

        for (int i = 1; i < cordNodes.size(); i++) {
            double temp_odl = cordNodes.get(i).minus(baseNode);
            if (temp_odl == 0) {
                return cordNodes.get(i).getCid();
            }
            if (min_odl > temp_odl) {
                min_id = i;
                min_odl = temp_odl;
            }
        }
        System.out.println(min_odl + "--");
        return cordNodes.get(min_id).getCid();
    }


}
