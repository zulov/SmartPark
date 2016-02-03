package response;

import entities.Parking;

import java.util.List;

/**
 * Created by Tomek on 2016-02-03.
 */
public class ParkListResponse extends ListResponse {
    List<Parking> parkings;

    public List<Parking> getParkings() {
        return parkings;
    }

    public void setParkings(List<Parking> parkings) {
        this.parkings = parkings;
    }

    public ParkListResponse(String message, Status status, Long time, int hits, List<Parking> parkings) {
        super(message, status, time, hits);
        this.parkings = parkings;
    }
}
