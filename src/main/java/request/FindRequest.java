package request;

import entities.Parking;

import java.io.Serializable;

/**
 * Created by Tomek on 2015-12-15.
 */
public class FindRequest extends ParkRequest implements Serializable {

    private String endLat;
    private String endLon;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getEndLon() {
        return endLon;
    }

    public void setEndLon(String endLon) {
        this.endLon = endLon;
    }
}
