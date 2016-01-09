package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Currency;

@Entity
@Table(name = "user_parking_data")
public class UserParkingData   extends AbstractEntity{

    @Column(name="park_time")
    private Timestamp parkTime;

    @Column(name="duration")
    private Long duration;// in minutes

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="parking_id")
    private Parking parking;

    public Timestamp getParkTime() {
        return parkTime;
    }

    public void setParkTime(Timestamp parkTime) {
        this.parkTime = parkTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
