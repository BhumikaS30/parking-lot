package com.parking.lot.models.parking;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class HourlyCost {

    private Map<ParkingSpotType, Double> hourlyCosts = new HashMap<>();

    public HourlyCost() {
        hourlyCosts.put(ParkingSpotType.CAR, 20.0);
        hourlyCosts.put(ParkingSpotType.LARGE, 30.0);
        hourlyCosts.put(ParkingSpotType.ELECTRIC, 25.0);
        hourlyCosts.put(ParkingSpotType.MOTORBIKE, 10.0);
        hourlyCosts.put(ParkingSpotType.ABLED, 25.0);
    }
    public double getCost(ParkingSpotType parkingSpotType) {
        return hourlyCosts.get(parkingSpotType);
    }

}
