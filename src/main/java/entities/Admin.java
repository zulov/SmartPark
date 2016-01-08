package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "admin")
public class Admin    extends AbstractEntity{

    @OneToOne
    @JoinColumn(name="login_id")
    private Account account;

    @OneToMany
    @JoinColumn(name="parking_ids")
    private List<Parking> parkings;

}
