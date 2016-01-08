package entities;

import javax.persistence.*;

/**
 * Created by Tomek on 2016-01-06.
 */
@Entity
@Table(name = "distances")
public class Distances extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name="start_node")
    private CordNode startNode;
    @ManyToOne
    @JoinColumn(name="end_node")
    private CordNode endNode;

    @Column(name="distance")
    private Integer distance;

    public Distances() {
    }

    public Distances(CordNode startNode, CordNode endNode, Integer distance) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.distance = distance;
    }

    public CordNode getStartNode() {
        return startNode;
    }

    public void setStartNode(CordNode startNode) {
        this.startNode = startNode;
    }

    public CordNode getEndNode() {
        return endNode;
    }

    public void setEndNode(CordNode endNode) {
        this.endNode = endNode;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
