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

    //@Column(name = "timestamp")
    //private Timestamp timestamp;

    @Column(name = "activity")
    private Boolean activity;

    public Boolean getActivity() {
        return activity;
    }

    public void setActivity(Boolean activity) {
        this.activity = activity;
    }

    public AbstractEntity() {
        java.util.Date date= new java.util.Date();
        //timestamp=new Timestamp(date.getTime());
        activity=true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
