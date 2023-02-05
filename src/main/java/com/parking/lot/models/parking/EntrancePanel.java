package com.parking.lot.models.parking;

import java.time.LocalDateTime;

import com.parking.lot.models.vehicle.Vehicle;

import lombok.Getter;

import static java.util.Objects.isNull;
import static java.util.UUID.randomUUID;

@Getter
public class EntrancePanel {

    private String id;

    public EntrancePanel(String id) {
        this.id = id;
    }

    public ParkingTicket getParkingTicket(Vehicle vehicle) {
        if (!ParkingLot.INSTANCE.canPark(vehicle.getVehicleType())) {
            return null;
        }
        ParkingSpot parkingSpot = ParkingLot.INSTANCE.getParkingSpot(vehicle.getVehicleType());
        if (isNull(parkingSpot)) {
            return null;
        }
        return buildTicket(vehicle.getLicenseNumber(), parkingSpot.getParkingSpotId());
    }

    private ParkingTicket buildTicket(String licenseNumber, String parkingSpotId) {
        ParkingTicket parkingTicket = new ParkingTicket();
        parkingTicket.setIssuedAt(LocalDateTime.now());
        parkingTicket.setAllottedParkingSpotId(parkingSpotId);
        parkingTicket.setLicenseNumber(licenseNumber);
        parkingTicket.setParkingTicketId(randomUUID().toString());
        parkingTicket.setTicketStatus(TicketStatus.ACTIVE);
        return parkingTicket;
    }
}
