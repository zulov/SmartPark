package algorithm;


import java.util.ArrayList;
import java.util.List;

public class DijkstraNode {

    int id;
    int distanceTo;//dlougosc drogi do
    int droga;

    Integer previous;
    List<DijkstraNode> neightbours;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDistanceTo() {
        return distanceTo;
    }

    public void setDistanceTo(int distanceTo) {
        this.distanceTo = distanceTo;
    }

    public int getDroga() {
        return droga;
    }

    public void setDroga(int droga) {
        this.droga = droga;
    }

    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }

    public void setPrevious(Integer previous) {
        this.previous = previous;
    }

    public List<DijkstraNode> getNeightbours() {
        return neightbours;
    }

    public void setNeightbours(List<DijkstraNode> neightbours) {
        this.neightbours = neightbours;
    }

    DijkstraNode(int dist, int id) {
        distanceTo = dist;
        neightbours = new ArrayList<>();
        this.id = id;
        droga = 200000000;
        previous = -1;
    }

    DijkstraNode() {
        neightbours = new ArrayList<>();
        droga = 200000000;
        previous = -1;
    }
}
