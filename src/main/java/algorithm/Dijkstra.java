package algorithm;

import entities.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Dijkstra {
    public Dijkstra() {
        threshold = 4000.0;
    }

    public Double getThreshold() {
        return threshold;
    }

    public Parking bestParking;
    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    private Double threshold;
    private final DistanceUtils distanceUtils = new DistanceUtils();

    void calculate(Map<Long, DijkstraNode> dijkstraNodes, Long start) {
        LinkedList<QueueElement> queue = new LinkedList<>();
        dijkstraNodes.get(start).setTotalDistance(0);
        QueueElement queueElement = createQueueElement(dijkstraNodes.get(start).getId(), 0);
        queue.add(queueElement);

        DijkstraNode temp = dijkstraNodes.get(start);

        while (!queue.isEmpty()) {
            queue.removeLast();
            for (DijkstraNode neighbour : temp.getNeightbours()) {
                DijkstraNode next = dijkstraNodes.get(neighbour.getId());
                if (next.getTotalDistance() > temp.getTotalDistance() + neighbour.getDistanceTo()) {
                    next.setTotalDistance(temp.getTotalDistance() + neighbour.getDistanceTo());

                    updateQueue(queue, neighbour.getId(), next.getTotalDistance());
                    next.setPrevious(temp.getId());
                }
            }

            if (queue.isEmpty() || threshold < queue.getLast().getWart()) {
                break;
            } else {
                temp = dijkstraNodes.get(queue.getLast().getId());
            }
        }
    }

    private QueueElement createQueueElement(Long id, int wart) {
        QueueElement queueElement = new QueueElement();
        queueElement.setId(id);
        queueElement.setWart(wart);
        return queueElement;
    }

    ArrayList<Long> showPath(Map<Long, DijkstraNode> dijkstraNodes, Long bw) {
        DijkstraNode dijkstraNode = dijkstraNodes.get(bw);

        ArrayList<Long> pathIds = new ArrayList<>(50);

        while (!dijkstraNode.getPrevious().equals(-1L)) {
            pathIds.add(dijkstraNode.getPrevious());
            dijkstraNode = dijkstraNodes.get(dijkstraNode.getPrevious());
        }
        return pathIds;
    }

    private void updateQueue(LinkedList<QueueElement> queue, Long id, int droga) {
        LinkedList<QueueElement> temp_v = new LinkedList<>();
        boolean found = false;
        while (!queue.isEmpty() && queue.getLast().getWart() < droga) {
            if (queue.getLast().getId().equals(id)) {
                found = true;
            }
            temp_v.add(queue.getLast());
            queue.removeLast();
        }

        if (found) {
            while (!queue.isEmpty()) {
                if (queue.getLast().getId().equals(id)) {
                    queue.removeLast();
                    break;
                }
            }
        } else {
            QueueElement q = createQueueElement(id, droga);
            queue.add(q);
        }

        while (temp_v.size() != 0) {
            queue.add(temp_v.getLast());
            temp_v.removeLast();
        }
    }

    public Integer getDistance(Map<Long, DijkstraNode> dijkstraNodes, Long bw) {
        DijkstraNode dijkstraNode = dijkstraNodes.get(bw);
        return dijkstraNode.getTotalDistance();
    }

    public Long findBestParking(ArrayList<CordNode> cordNodes, Map<Long,Parking> parkings, Map<Long, DijkstraNode> dijkstraNodes, List<AbstractEntity> userParkingDatas) {
        float bestParkingScore = 200000000;

        DijkstraNode closetToBest=null;
        for (Parking parking: parkings.values()) {
            if (parking.getFreeSlots() < parking.getTotalSlots()) {
                Long closestId = distanceUtils.getClosestToNode(parking.getLocalization().getLat(), parking.getLocalization().getLon(), cordNodes,parkings,10000);
                DijkstraNode findNode = dijkstraNodes.get(closestId);
                float score = findNode.getTotalDistance();
                score *=1/getModifier(parking,userParkingDatas);
                if (score<bestParkingScore){
                    closetToBest=findNode;
                    bestParkingScore=score;
                    bestParking=parking;
                }
            }
        }
        if (closetToBest != null) {
            return closetToBest.getId();
        } else {
            return null;
        }

    }

    private float getModifier(Parking parking, List<AbstractEntity> userParkingDatas) {
        float counter=1;
        java.util.Date date= new java.util.Date();
        Timestamp currentTimestamp=new Timestamp(date.getTime());
        for(AbstractEntity ab:userParkingDatas){
            UserParkingData userParkingData=(UserParkingData)ab;
            if(parking.getId()==userParkingData.getParking().getId()){
                float diff=Math.abs((currentTimestamp.getTime()/(1000.f*60*60)%24)-(userParkingData.getParkTime().getTime()/(1000.f*60*60)%24));
                counter+=1/diff;
            }
        }
        return counter;
    }
}
