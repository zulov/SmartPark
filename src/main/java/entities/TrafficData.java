package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "traffic_data")
public class TrafficData    extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name="start_node")
    private CordNode startNode;
    @ManyToOne
    @JoinColumn(name="end_node")
    private CordNode endNode;

    @Column(name="busy_factor")
    private Float busyFactor;

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

    public Float getBusyFactor() {
        return busyFactor;
    }

    public void setBusyFactor(Float busyFactor) {
        this.busyFactor = busyFactor;
    }
}
