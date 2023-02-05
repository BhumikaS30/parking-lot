package com.parking.lot.models.parking;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import com.parking.lot.models.account.Address;
import com.parking.lot.models.vehicle.VehicleType;

import lombok.Getter;
import lombok.Setter;

import static com.parking.lot.models.parking.ParkingFloor.getSpotTypeForVehicle;
import static java.util.Objects.nonNull;

@Getter
@Setter
public class ParkingLot {

    private String parkingLotId;

    private Address address;

    private List<ParkingFloor> parkingFloors;

    private List<EntrancePanel> entrancePanels;

    private List<ExitPanel> exitPanels;

    public static ParkingLot INSTANCE = new ParkingLot();

    private ParkingLot() {
        this.parkingFloors = new ArrayList<>();
        this.entrancePanels = new ArrayList<>();
        this.exitPanels = new ArrayList<>();
    }

    public boolean isFull() {
        int index = 0;
        BitSet bitSet = new BitSet();
        for (ParkingFloor parkingFloor : parkingFloors) {
            bitSet.set(index++, parkingFloor.isFloorFull());
        }
        return bitSet.cardinality() == bitSet.size();
    }

    public boolean canPark(VehicleType vehicleType) {
        for (ParkingFloor parkingFloor : parkingFloors) {
            if (parkingFloor.canPark(getSpotTypeForVehicle(vehicleType))) {
                return true;
            }
        }
        return false;
    }

    public ParkingSpot getParkingSpot(VehicleType vehicleType) {
        for (ParkingFloor parkingFloor : parkingFloors) {
            ParkingSpot parkingSpot =  parkingFloor.getSpot(vehicleType);
            if (nonNull(parkingSpot)) {
                return parkingSpot;
            }
        }
        return null;
    }

    public ParkingSpot vacateParkingSpot(String parkingSpotId) {
        for (ParkingFloor parkingFloor : parkingFloors) {
            ParkingSpot parkingSpot =  parkingFloor.vacateParkingSpot(parkingSpotId);
            if (nonNull(parkingSpot)) {
                return parkingSpot;
            }
        }
        return null;
    }
}
