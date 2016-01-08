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
import java.util.ArrayList;

@Entity
@Table(name = "cord_node",
        indexes = {
        @Index(columnList = "cid", name = "cid_inx1")}
)
public class CordNode extends AbstractEntity {
    @Column(name="cid")
    int cid;
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

    public int getCid() {
        return cid;
    }

    public void setCid(int id) {
        this.cid = id;
    }

    public CordNode(int a, double x, double y) {
        cid = a;
        lon = y;
        lat = x;
    }

    public int minus(CordNode b) {
        double PI = 3.1415;
        double wynik=(sqrt(pow(cos(PI * lat / 180) * (b.lon - lon), 2) + pow((b.lat - lat), 2)) * PI * 12765.274 / 360) * 1000;
        return (int)wynik;
    }

    public static CordNode find_node(ArrayList<CordNode> node_list, int a) {
        int d = node_list.size();

        int ip = 0;
        int ik = d - 1;
        int isr;
        while (ip <= ik) {

            isr = (ip + ik) >> 1;// szybkie dzielenie na dwa
            if (node_list.get(isr).getCid() == a) {
                // cout<<"1";
                return node_list.get(isr);
            } else if (a < node_list.get(isr).getCid()) {
                ik = isr - 1;
            } else {
                ip = isr + 1;
            }
        }
        return new CordNode(0, 0, 0);
    }

}
