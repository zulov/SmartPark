package algorithm;


import java.util.ArrayList;

/**
 * Created by Tomek on 2015-12-15.
 */

public class ListUtils {
    public ListUtils() {
    }

    int findNode(ArrayList<DijkstraNode> dijkstraNodes, int id) {
        int d = dijkstraNodes.size();

        int ip = 0;
        int ik = d - 1;
        int isr;
        while (ip <= ik) {
            isr = (ip + ik) >> 1;// szybkie dzielenie na dwa
            if (dijkstraNodes.get(isr).getId() == id) {
                return isr;
            } else if (id < dijkstraNodes.get(isr).getId()) {
                ik = isr - 1;
            } else {
                ip = isr + 1;
            }
        }
        return 0;
    }

    int findNodeSimple(ArrayList<DijkstraNode> dijkstraNodes, int id) {
        int d = dijkstraNodes.size();
        int i;
        for (i = 0; i < d; i++) {
            if (dijkstraNodes.get(i).getId() == id) {
                return i;
            }
        }
        return 0;
    }
}
