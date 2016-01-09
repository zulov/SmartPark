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
    @JoinColumn(name="admin_parking")
    private List<Parking> parkings;

}
