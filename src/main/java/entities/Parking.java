package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "parking")
public class Parking    extends AbstractEntity{
    @OneToOne
    @JoinColumn(name="localization_id")
    private CordNode localization;

    @Column(name="free_slots")
    private Long freeSlots;

    @Column(name="total_slots")
    private Long totalSlots;

    public CordNode getLocalization() {
        return localization;
    }

    public void setLocalization(CordNode localization) {
        this.localization = localization;
    }

    public Long getFreeSlots() {
        return freeSlots;
    }

    public void setFreeSlots(Long freeSlots) {
        this.freeSlots = freeSlots;
    }

    public Long getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(Long totalSlots) {
        this.totalSlots = totalSlots;
    }
    public void incFreeSlot(){
        if(freeSlots<totalSlots){
            freeSlots++;
        }
    }

    public void decFreeSlot(){
        if(freeSlots>1){
            freeSlots--;
        }
    }


}
