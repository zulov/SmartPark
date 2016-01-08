package algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
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


    void find(Map<Long, DijkstraNode> dijkstraNodes, Long start) {
        LinkedList<QueueElement> queue = new LinkedList<>();
        dijkstraNodes.get(start).setDroga(0);
        QueueElement queueElement = createQueueElement(dijkstraNodes.get(start).getId(), 0);
        queue.add(queueElement);

        DijkstraNode temp = dijkstraNodes.get(start);

        while (!queue.isEmpty()) {
            queue.removeLast();
            for (DijkstraNode neighbour : temp.getNeightbours()) {
                DijkstraNode next = dijkstraNodes.get(neighbour.getId());
                if (next.getDroga() > temp.getDroga() + neighbour.getDistanceTo()) {
                    next.setDroga(temp.getDroga() + neighbour.getDistanceTo());

                    update_kol(queue, neighbour.getId(), next.getDroga());
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

    ArrayList<Long>  showPath(Map<Long, DijkstraNode> dijkstraNodes, Long bw) {
        DijkstraNode dijkstraNode = dijkstraNodes.get(bw);

        ArrayList<Long> pathIds = new ArrayList<>(50);

        while (!dijkstraNode.getPrevious().equals(-1L)) {
            pathIds.add(dijkstraNode.getPrevious());
            dijkstraNode = dijkstraNodes.get(dijkstraNode.getPrevious());
        }
        return pathIds;
//        ArrayList<CordNode> path = new ArrayList<>(pathIds.size());
//
//        for (int i = pathIds.size() - 1; i >= 0; i--) {
//            CordNode tn = documentRepository.getCordNode(pathIds.get(i));
//            path.add(tn);
//        }
//        return path;
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
        return dijkstraNode.getDroga();
    }
}
