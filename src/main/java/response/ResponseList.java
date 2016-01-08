package response;

import entities.AbstractEntity;
import entities.CordNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomek on 2015-11-22.
 */
public class ResponseList {
    int hits;
    int distance;
    Long time;
    List<CordNode>entities;

    public List<CordNode> getEntities() {
        return entities;
    }

    public void setEntities(List<CordNode> entities) {
        this.entities = entities;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
