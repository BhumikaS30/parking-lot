package com.parking.lot.models.parking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingSpot {

    private String parkingSpotId;

    private ParkingSpotType parkingSpotType;

    private boolean isFree;

    private String assignedVehicleId;

    public ParkingSpot(String parkingSpotId, ParkingSpotType parkingSpotType) {
        this.parkingSpotId = parkingSpotId;
        this.parkingSpotType = parkingSpotType;
    }

    public void assignParkingSpot(String vehicleId) {
        this.assignedVehicleId = vehicleId;
    }

    public void freeSpot() {
        this.isFree = true;
        this.assignedVehicleId = null;
    }
}
