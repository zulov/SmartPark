package entities;


import javax.persistence.*;
import java.util.Currency;
import java.util.List;

@Entity
@Table(name = "users")
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Currency getCash() {
        return cash;
    }

    public void setCash(Currency cash) {
        this.cash = cash;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
