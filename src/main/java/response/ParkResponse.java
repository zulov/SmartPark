package response;

/**
 * Created by Tomek on 2016-01-20.
 */
public class ParkResponse extends BasicResponse {
    private Long parkId;

    public Long getParkId() {
        return parkId;
    }

    public void setParkId(Long parkId) {
        this.parkId = parkId;
    }

    public ParkResponse(String message, Status status, Long time, Long parkId) {
        super(message, status, time);
        this.parkId = parkId;
    }
}
