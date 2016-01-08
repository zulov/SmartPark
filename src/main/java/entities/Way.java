package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
@Deprecated
@Entity
@Table(name = "way")
public class Way extends AbstractEntity {
    @Column(name="wid")
    int wid;
    @Column(name="name")
    String name;
    @ManyToMany
    @Column(name="path")
    List<CordNode> path;

    public Way() {
    }
    @Column(name="oneway")
    boolean oneway;

    public Way(int wid) {
        this.wid = wid;
        oneway = false;
        path = new ArrayList<>();
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int id) {
        this.wid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CordNode> getPath() {
        return path;
    }

    public void setPath(List<CordNode> path) {
        this.path = path;
    }

    public boolean isOneway() {
        return oneway;
    }

    public void setOneway(boolean oneway) {
        this.oneway = oneway;
    }

}
