package algorithm;

import entities.AbstractEntity;
import entities.CordNode;
import entities.Parking;

import java.util.ArrayList;
import java.util.List;

public class DistanceUtils {

    public DistanceUtils() {

    }
    Long getClosestToNode(double lat, double lon,ArrayList<CordNode> cordNodes,List<AbstractEntity> parkings) {
        CordNode baseNode = new CordNode(1L, lat, lon);

        double min_odl = cordNodes.get(0).minus(baseNode);
        CordNode closest = cordNodes.get(0);
        for (CordNode cordNode : cordNodes) {
            boolean flag=false;
            for(AbstractEntity ab:parkings){
                Parking parking = (Parking) ab;
                if (parking.getLocalization().getCid()==cordNode.getCid()){
                    flag=true; break;
                }
            }
            if(flag){continue;}
            double temp_odl = cordNode.minus(baseNode);
            if (min_odl > temp_odl) {
                closest = cordNode;
                min_odl = temp_odl;
            }
        }
        System.out.println(min_odl + "--");
        return closest.getCid();
    }

    Long getClosest(double lat, double lon,ArrayList<CordNode> cordNodes) {
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

    Long getClosest(String _Lat, String _Lon,ArrayList<CordNode> cordNodes) {
        double lat = Double.parseDouble(_Lat);
        double lon = Double.parseDouble(_Lon);
        return getClosest(lat,lon,cordNodes);
    }

}