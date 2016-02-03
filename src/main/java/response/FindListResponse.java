package response;

import entities.AbstractEntity;
import entities.CordNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomek on 2015-11-22.
 */
public class FindListResponse extends ListResponse {
    Integer distance;
    List<CordNode>cords;

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public List<CordNode> getCords() {
        return cords;
    }

    public void setCords(List<CordNode> cords) {
        this.cords = cords;
    }

    public FindListResponse(String message, Status status, Long time, int hits, Integer distance, List<CordNode> cords) {
        super(message, status, time, hits);
        this.distance = distance;
        this.cords = cords;
    }
}
