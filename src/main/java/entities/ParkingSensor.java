package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "parking_sensor")
public class ParkingSensor  extends AbstractEntity {

    @OneToOne
    @JoinColumn(name="localization_id")
    private Localization localization;

    @Column(name="occupation")
    private Boolean occupation;

    @ManyToOne
    @JoinColumn(name="parking_id")
    private Parking parking;
}
