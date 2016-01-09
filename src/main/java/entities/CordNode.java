/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.*;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


@Entity
@Table(name = "cord_node",
        indexes={
        @Index(columnList = "cid", name="cn_cid_inx")}
)
public class CordNode extends AbstractEntity {
    @Column(name="cid")
    Long cid;
    @Column(name="lat")
    double lat;
    @Column(name="lon")
    double lon;

    public CordNode() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public CordNode(Long a, double x, double y) {
        cid = a;
        lon = y;
        lat = x;
    }

    public int minus(CordNode b) {
        double PI = 3.1415;
        double wynik=(sqrt(pow(cos(PI * lat / 180) * (b.lon - lon), 2) + pow((b.lat - lat), 2)) * PI * 12765.274 / 360) * 1000;
        return (int)wynik;
    }

}
