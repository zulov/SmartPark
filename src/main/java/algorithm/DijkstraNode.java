package algorithm;


import java.util.ArrayList;
import java.util.List;

public class DijkstraNode {

    Long id;
    int distanceTo;//dlougosc drogi do
    int totalDistance;

    Long previous;
    List<DijkstraNode> neightbours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDistanceTo() {
        return distanceTo;
    }

    public void setDistanceTo(int distanceTo) {
        this.distanceTo = distanceTo;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Long getPrevious() {
        return previous;
    }

    public void setPrevious(Long previous) {
        this.previous = previous;
    }

    public List<DijkstraNode> getNeightbours() {
        return neightbours;
    }

    public void setNeightbours(List<DijkstraNode> neightbours) {
        this.neightbours = neightbours;
    }

    DijkstraNode(int dist, Long id) {
        distanceTo = dist;
        neightbours = new ArrayList<>();
        this.id = id;
        totalDistance = 200000000;
        previous = -1L;
    }

    DijkstraNode() {
        neightbours = new ArrayList<>();
        totalDistance = 200000000;
        previous = -1L;
    }
}
