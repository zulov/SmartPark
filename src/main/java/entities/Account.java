package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account extends AbstractEntity {

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;


}
