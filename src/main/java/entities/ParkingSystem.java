package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "parking_system")
@Deprecated
public class ParkingSystem    extends AbstractEntity {

    @Column(name="name")
    private String name;

}
