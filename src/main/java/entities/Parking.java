package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "parking")
public class Parking    extends AbstractEntity{
    @OneToOne
    @JoinColumn(name="localization_id")
    private Localization localization;

    @Column(name="free_slots")
    private Long freeSlots;

    @Column(name="total_slots")
    private Long totalSlots;



}
