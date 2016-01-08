package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Currency;

@Entity
@Table(name = "vehicle")
public class Vehicle    extends AbstractEntity{

    @Column(name="name")
    private String name;

    @Column(name="reg_plate")
    private String regPlate;
}
