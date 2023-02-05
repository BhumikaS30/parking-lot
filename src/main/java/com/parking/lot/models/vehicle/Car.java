package com.parking.lot.models.vehicle;

import static com.parking.lot.models.vehicle.VehicleType.CAR;

public class Car extends Vehicle{

    public Car(String licenseNumber) {
        super(licenseNumber, CAR);
    }
}
