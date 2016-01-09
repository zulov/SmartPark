package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "personal_data")
public class PersonalData    extends AbstractEntity {
    @Column(name="firstname")
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    @Column(name="birth_date")
    private Date birthDate;

    @Column(name="cell_phone")
    private String cellphone;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public PersonalData() {
    }

    public PersonalData(String firstname, String lastname, Date birthDate, String cellphone, User user) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.cellphone = cellphone;
        this.user = user;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
