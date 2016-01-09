package entities;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
public class AbstractEntity  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "activity")
    private Boolean activity;

    public Boolean getActivity() {
        return activity;
    }

    public void setActivity(Boolean activity) {
        this.activity = activity;
    }

    public AbstractEntity() {
        activity=true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
