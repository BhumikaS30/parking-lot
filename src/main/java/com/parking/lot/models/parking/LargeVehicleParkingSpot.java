package com.parking.lot.models.parking;

import static com.parking.lot.models.parking.ParkingSpotType.LARGE;

public class LargeVehicleParkingSpot extends ParkingSpot {
    public LargeVehicleParkingSpot(String id) {
        super(id, LARGE);
    }
}
