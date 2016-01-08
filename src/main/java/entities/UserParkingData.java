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

}
