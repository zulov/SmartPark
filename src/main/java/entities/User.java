package entities;


import javax.persistence.*;
import java.util.Currency;
import java.util.List;

@Entity
@Table(name = "user")
public class User    extends AbstractEntity {
    @OneToOne
    @JoinColumn(name="account_id")
    private Account account;

    @Column(name="cash")
    private Currency cash;

    @Column(name="vip")
    private Boolean vip;

    @ManyToMany
    @JoinColumn(name="vechicles_id")
    private List<Vehicle> vehicles;

}
