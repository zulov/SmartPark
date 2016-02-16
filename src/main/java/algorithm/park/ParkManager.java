package algorithm.park;

import algorithm.DistanceUtils;
import entities.*;
import repositories.DocumentRepository;
import request.ParkRequest;
import response.ParkListResponse;
import response.ParkResponse;
import response.Status;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomek on 2016-01-20.
 */
@Singleton
public class ParkManager {
    private final float threshold = 2000.0f;
    @Inject
    private DocumentRepository documentRepository;
    private final DistanceUtils distanceUtils = new DistanceUtils();

    private List<AbstractEntity> parkings;
    private ArrayList<CordNode> cordNodesParkings;

    public ParkResponse park(ParkRequest request) {
        long startTime = System.currentTimeMillis();
        initDataFromDB();
        ParkResponse pr;

        if(validateParkIn(request)){
            pr = new ParkResponse("", Status.PARKIN,System.currentTimeMillis() - startTime,0L);
        }else{
            pr = new ParkResponse("Parkowanie się nie powidło", Status.ERROR,System.currentTimeMillis() - startTime,null);
        }

        return pr;
    }


    public ParkResponse unpark(ParkRequest request) {
        long startTime = System.currentTimeMillis();
        initDataFromDB();
        ParkResponse pr;

        if (validateParkOut(request)) {
            pr = new ParkResponse("", Status.SUCCESS, System.currentTimeMillis() - startTime, 0L);
        } else {
            pr = new ParkResponse("odparkowanie się nie powidło", Status.ERROR, System.currentTimeMillis() - startTime, null);
        }
        return pr;
    }

    private Boolean validateParkIn(ParkRequest request) {
        Long parkingId = distanceUtils.getClosest(request.getLat(), request.getLon(), cordNodesParkings, 80);
        if (parkingId < 0) {
            return false;
        }
        CordNode cn = cordNodesParkings.stream().filter(v -> v.getCid().equals(parkingId)).findFirst().get();
        Parking parking = documentRepository.getParking((long) cn.getId());
        if (parking.getFreeSlots() <= 0) {
            return false;
        }
        parking.decFreeSlot();
        UserParkingData userParkingData = new UserParkingData();
        userParkingData.setParking(parking);
        java.util.Date date = new java.util.Date();
        Timestamp currentTimestamp = new Timestamp(date.getTime());
        userParkingData.setParkTime(currentTimestamp);
        User user = (User) documentRepository.get("User", "id", request.getUserId().toString());
        userParkingData.setUser(user);
        documentRepository.save(userParkingData);
        documentRepository.save(parking);
        return true;
    }

    private Boolean validateParkOut(ParkRequest request) {
        Long parkingId = distanceUtils.getClosest(request.getLat(), request.getLon(), cordNodesParkings, 30);
        if (parkingId < 0) {
            return false;
        }
        CordNode cn = cordNodesParkings.stream().filter(v -> v.getCid().equals(parkingId)).findFirst().get();
        Parking parking = documentRepository.getParking((long) cn.getId());
        parking.incFreeSlot();
        documentRepository.save(parking);
        return true;
    }

    private void initDataFromDB() {
        if (parkings == null) {
            parkings = documentRepository.getAll("Parking");
            cordNodesParkings = new ArrayList<>(parkings.size());
            for (AbstractEntity ab : parkings) {
                Parking parking = (Parking) ab;
                cordNodesParkings.add(parking.getLocalization());
            }
        }
    }

    public ParkListResponse getParkings(String _lat, String _lon) {
        long startTime = System.currentTimeMillis();
        initDataFromDB();
        List<Parking> parkingsInRange = new ArrayList<>(20);
        CordNode cordNode = new CordNode();
        cordNode.setLat(Double.parseDouble(_lat));
        cordNode.setLon(Double.parseDouble(_lon));

        for (AbstractEntity ab : parkings) {
            Parking parking = (Parking) ab;
            if (parking.getLocalization().minus(cordNode) < threshold) {
                parkingsInRange.add(parking);
            }
        }
        ParkListResponse prl = new ParkListResponse("", Status.SUCCESS, System.currentTimeMillis() - startTime, parkingsInRange.size(), parkingsInRange);
        return prl;
    }

    public ParkListResponse getAllParkings() {
        long startTime = System.currentTimeMillis();
        initDataFromDB();
        List<Parking> carparks = new ArrayList<>();
        for (AbstractEntity ab : parkings) {
            Parking parking = (Parking) ab;
            carparks.add(parking);
        }

        ParkListResponse prl = new ParkListResponse("", Status.SUCCESS, System.currentTimeMillis() - startTime, carparks.size(), carparks);
        return prl;
    }
}
