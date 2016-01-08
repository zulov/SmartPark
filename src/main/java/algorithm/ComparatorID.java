package algorithm;

import java.util.Comparator;


public class ComparatorID implements Comparator<DijkstraNode> {

    @Override
    public int compare(DijkstraNode n1, DijkstraNode n2) {
        return  n1.getId()-n2.getId();
    }
}
