package algorithm;

import entities.CordNode;

import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tomek on 2016-02-12.
 */
@Singleton
public class SensorMock {
    private Map<Long,CordNode> prevPosition;

    public Boolean ifPark(Long userId, CordNode cn){
        if(prevPosition.containsKey(userId)){
            if(prevPosition.get(userId).minus(cn)<50){
                prevPosition.put(userId,cn);
                return true;
            }else{
                prevPosition.put(userId,cn);
                return false;
            }
        }else{
            prevPosition.put(userId,cn);
            return false;
        }
    }

    public SensorMock() {
        prevPosition= new HashMap<>(10);
    }

    public Map<Long, CordNode> getPrevPosition() {
        return prevPosition;
    }

    public void setPrevPosition(Map<Long, CordNode> prevPosition) {
        this.prevPosition = prevPosition;
    }
}
