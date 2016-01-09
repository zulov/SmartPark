package algorithm;

import entities.AbstractEntity;
import entities.CordNode;
import entities.Parking;
import entities.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Dijkstra {
    public Dijkstra() {
        threshold = 5000.0;
    }

    public Double getThreshold() {
        return threshold;
    }

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

                    update_kol(queue, neighbour.getId(), next.getTotalDistance());
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

    void update_kol(LinkedList<QueueElement> queue, Long id, int droga) {
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

    public Long findBestParking(ArrayList<CordNode> cordNodes, List<AbstractEntity> parkings,Map<Long, DijkstraNode> dijkstraNodes) {
        float bestParkingScore = 200000000;
        Parking bestParking = null;
        DijkstraNode closetToBest=null;
        for (int i = 0; i < parkings.size(); i++) {
            Parking parking = (Parking) parkings.get(i);
            if (parking.getFreeSlots() < parking.getTotalSlots()) {
                Long closestId = distanceUtils.getClosestToNode(parking.getLocalization().getLat(), parking.getLocalization().getLon(), cordNodes,parkings);
                DijkstraNode findNode = dijkstraNodes.get(closestId);
                float score = findNode.getTotalDistance();
                //score *=cos tam;
                if (score<bestParkingScore){
                    closetToBest=findNode;
                    bestParkingScore=score;
                }
            }

        }
        if (closetToBest != null) {
            return closetToBest.getId();
        } else {
            return null;
        }

    }
}
