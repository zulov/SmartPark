package algorithm;

import entities.CordNode;
import repositories.DocumentRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;


public class Dijkstra {
    public Dijkstra() {
        threshold = 5000.0;
    }

    private DocumentRepository documentRepository;

    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    private Double threshold;


    void find(Map<Integer,DijkstraNode> dijkstraNodes, int start) {
        LinkedList<QueueElement> queue = new LinkedList<>();
        dijkstraNodes.get(start).setDroga(0);
        QueueElement queueElement = createQueueElement(dijkstraNodes.get(start).getId(), 0);
        queue.add(queueElement);

        DijkstraNode temp = dijkstraNodes.get(start);

        while (queue.size() != 0) {
            queue.removeLast();
            for(DijkstraNode neighbour :temp.getNeightbours()){
                DijkstraNode next= dijkstraNodes.get(neighbour.getId());
                if (next.getDroga() > temp.getDroga() + neighbour.getDistanceTo()) {
                    next.setDroga(temp.getDroga() + neighbour.getDistanceTo());

                    update_kol(queue, neighbour.getId(), next.getDroga());
                    next.setPrevious(temp.getId());
                }
            }

            if (queue.isEmpty() || threshold < queue.getLast().getWart()){
                break;
            }else{
                temp =  dijkstraNodes.get(queue.getLast().getId());
            }

        }

    }

    private QueueElement createQueueElement(int id, int wart) {
        QueueElement queueElement = new QueueElement();
        queueElement.setId(id);
        queueElement.setWart(wart);
        return queueElement;
    }

    ArrayList<CordNode> showPath(Map<Integer,DijkstraNode> dijkstraNodes, int bw) {
        DijkstraNode dijkstraNode=dijkstraNodes.get(bw);

        ArrayList<Integer> droga = new ArrayList<>();

        while (dijkstraNode.getPrevious() != -1) {
            droga.add(dijkstraNode.getPrevious());//System.out.println(lista.get(i).previous<<endl;
            dijkstraNode = dijkstraNodes.get( dijkstraNode.getPrevious());
        }
        ArrayList<CordNode> path = new ArrayList<>(droga.size());
        int d = droga.size();
        for (int i = d - 1; i >= 0; i--) {
            CordNode tn =documentRepository.getCordNode(droga.get(i));
            path.add(tn);
        }
        return path;
    }

    void update_kol(LinkedList<QueueElement> kol_pri, int id, int droga) {
        LinkedList<QueueElement> temp_v = new LinkedList<>();
        boolean found = false;
        while (kol_pri.size() != 0 && kol_pri.getLast().getWart() < droga) {
            if (kol_pri.getLast().getId() == id) {
                found = true;
            }
            temp_v.add(kol_pri.getLast());
            kol_pri.removeLast();
        }
        if (found) {
            while (kol_pri.size() != 0) {
                if (kol_pri.getLast().getId() == id) {
                    kol_pri.removeLast();
                    break;
                }
            }
        } else {
            QueueElement q = createQueueElement(id,droga);
            kol_pri.add(q);
        }

        while (temp_v.size() != 0) {
            kol_pri.add(temp_v.getLast());
            temp_v.removeLast();
        }
    }
}
