package com.parking.lot.models.vehicle;

import com.parking.lot.models.parking.ParkingTicket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Vehicle {

    private String id;

    private String licenseNumber;

    private VehicleType vehicleType;

    private ParkingTicket parkingTicket;

    public Vehicle(String licenseNumber, VehicleType vehicleType) {
        this.licenseNumber = licenseNumber;
        this.vehicleType = vehicleType;
    }
}
