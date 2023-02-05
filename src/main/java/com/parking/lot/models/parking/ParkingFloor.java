package com.parking.lot.models.parking;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.parking.lot.models.vehicle.VehicleType;

import lombok.Getter;
import lombok.Setter;

import static com.parking.lot.models.parking.ParkingSpotType.ABLED;
import static com.parking.lot.models.parking.ParkingSpotType.CAR;
import static com.parking.lot.models.parking.ParkingSpotType.ELECTRIC;
import static com.parking.lot.models.parking.ParkingSpotType.LARGE;
import static com.parking.lot.models.parking.ParkingSpotType.MOTORBIKE;
import static java.util.Objects.nonNull;

public class ParkingFloor {

    @Getter
    @Setter
    private String floorId;

    @Getter
    private Map<ParkingSpotType, Deque<ParkingSpot>> parkingSpots = new HashMap<>();

    @Getter
    private Map<String, ParkingSpot> usedParkingSpots = new HashMap<>();

    public ParkingFloor(String floorId) {
        this.floorId = floorId;
        parkingSpots.put(ABLED, new ConcurrentLinkedDeque<>());
        parkingSpots.put(CAR, new ConcurrentLinkedDeque<>());
        parkingSpots.put(LARGE, new ConcurrentLinkedDeque<>());
        parkingSpots.put(MOTORBIKE, new ConcurrentLinkedDeque<>());
        parkingSpots.put(ELECTRIC, new ConcurrentLinkedDeque<>());
    }

    public boolean isFloorFull() {
        for (Map.Entry<ParkingSpotType, Deque<ParkingSpot>> entry : parkingSpots.entrySet()) {
            if (entry.getValue().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static ParkingSpotType getSpotTypeForVehicle(VehicleType vehicleType) {
        return switch (vehicleType) {
            case CAR -> CAR;
            case ELECTRIC -> ELECTRIC;
            case MOTORBIKE -> MOTORBIKE;
            default -> LARGE;
        };
    }

    public boolean canPark(ParkingSpotType spotTypeForVehicle) {
        return !parkingSpots.get(spotTypeForVehicle).isEmpty();
    }

    public synchronized ParkingSpot getSpot(VehicleType vehicleType) {
        ParkingSpotType spotTypeForVehicle = getSpotTypeForVehicle(vehicleType);

        if (!canPark(spotTypeForVehicle)) {
           return null;
        }
        ParkingSpot parkingSpot = parkingSpots.get(spotTypeForVehicle).poll();
        usedParkingSpots.put(parkingSpot.getParkingSpotId(), parkingSpot);
        return parkingSpot;
    }

    public ParkingSpot vacateParkingSpot(String parkingSpotId) {
        ParkingSpot parkingSpot = usedParkingSpots.remove(parkingSpotId);
        if (nonNull(parkingSpot)) {
            parkingSpot.freeSpot();
            parkingSpots.get(parkingSpot.getParkingSpotType())
                .addFirst(parkingSpot);
            return parkingSpot;
        }
        return null;
    }
}
