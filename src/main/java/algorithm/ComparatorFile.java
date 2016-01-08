package algorithm;

import java.util.Comparator;


public class ComparatorFile  implements Comparator<ToSort>{
    @Override
    public int compare(ToSort n1, ToSort n2) {
        return  n1.getId()-n2.getId();
    }
}
