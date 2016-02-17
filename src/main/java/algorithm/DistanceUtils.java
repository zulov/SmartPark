package algorithm;

import entities.AbstractEntity;
import entities.CordNode;
import entities.Parking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DistanceUtils {

    public DistanceUtils() {

    }

    public Long getClosestToNode(double lat, double lon, ArrayList<CordNode> cordNodes, Map<Long, Parking> parkings, int threshold) {
        CordNode baseNode = new CordNode(1L, lat, lon);

        double min_odl = cordNodes.get(0).minus(baseNode);
        CordNode closest = cordNodes.get(0);
        for (CordNode cordNode : cordNodes) {
            if (parkings.containsKey(cordNode.getCid())) {
                continue;
            }

            double temp_odl = cordNode.minus(baseNode);
            if (min_odl > temp_odl) {
                closest = cordNode;
                min_odl = temp_odl;
            }
        }
        if (min_odl > threshold) {
            return -1L;
        }
        return closest.getCid();
    }

    public Long getClosest(double lat, double lon, ArrayList<CordNode> cordNodes, int threshold) {
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
        //System.out.println(min_odl);
        if (min_odl > threshold) {
            return -1L;
        }
        return closest.getCid();
    }

    public Long getClosest(String _Lat, String _Lon, ArrayList<CordNode> cordNodes, int threshold) {
        double lat = Double.parseDouble(_Lat);
        double lon = Double.parseDouble(_Lon);
        return getClosest(lat, lon, cordNodes, threshold);
    }

    public Long getClosestToNode(double lat, double lon, ArrayList<CordNode> cordNodes, Map<Long, Parking> parkings, int threshold, Map<Long, DijkstraNode> dijkstraNodes) {
        CordNode baseNode = new CordNode(1L, lat, lon);

        double min_odl = cordNodes.get(0).minus(baseNode);
        CordNode closest = cordNodes.get(0);
        for (CordNode cordNode : cordNodes) {
            if (parkings.containsKey(cordNode.getCid())) {
                continue;
            }
            if(dijkstraNodes.containsKey(cordNode.getCid())
                    &&dijkstraNodes.get(cordNode.getCid()).getTotalDistance()>1000000){
                continue;
            }
            double temp_odl = cordNode.minus(baseNode);
            if (min_odl > temp_odl) {
                closest = cordNode;
                min_odl = temp_odl;
            }
        }
        if (min_odl > threshold) {
            return -1L;
        }
        return closest.getCid();
    }
}