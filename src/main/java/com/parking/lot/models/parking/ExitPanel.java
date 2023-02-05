package com.parking.lot.models.parking;

import java.time.Duration;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ExitPanel {

    private String id;

    public ExitPanel(String id) {
        this.id = id;
    }

    public ParkingTicket scanAndVacate(ParkingTicket parkingTicket) {
        ParkingSpot parkingSpot = ParkingLot.INSTANCE.vacateParkingSpot(parkingTicket.getAllottedParkingSpotId());
        parkingTicket.setCharges(calculateCost(parkingTicket, parkingSpot.getParkingSpotType()));
        return parkingTicket;
    }

    private double calculateCost(ParkingTicket parkingTicket, ParkingSpotType parkingSpotType) {
        Duration duration = Duration.between(parkingTicket.getIssuedAt(), LocalDateTime.now());
        long hours = duration.toHours();
        if (hours == 0) {
            hours = 1;
        }
        return hours * new HourlyCost().getCost(parkingSpotType);
    }
}
