package com.parking.lot.models.parking;

import static com.parking.lot.models.parking.ParkingSpotType.ABLED;

public class AbledParkingSpot extends ParkingSpot {

    public AbledParkingSpot(String id) {
        super(id, ABLED);
    }
}
