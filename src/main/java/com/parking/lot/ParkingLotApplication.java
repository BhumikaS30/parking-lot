package com.parking.lot;

import java.util.UUID;

import com.parking.lot.exceptions.InvalidParkingFloorException;
import com.parking.lot.models.account.Address;
import com.parking.lot.models.account.User;
import com.parking.lot.models.account.UserType;
import com.parking.lot.models.parking.CarParkingSpot;
import com.parking.lot.models.parking.EntrancePanel;
import com.parking.lot.models.parking.ExitPanel;
import com.parking.lot.models.parking.MotorBikeParkingSpot;
import com.parking.lot.models.parking.ParkingFloor;
import com.parking.lot.models.parking.ParkingLot;
import com.parking.lot.models.parking.ParkingSpot;
import com.parking.lot.models.parking.ParkingSpotType;
import com.parking.lot.models.parking.ParkingTicket;
import com.parking.lot.models.parking.Payment;
import com.parking.lot.models.vehicle.Car;
import com.parking.lot.models.vehicle.MotorBike;
import com.parking.lot.models.vehicle.Van;
import com.parking.lot.models.vehicle.Vehicle;
import com.parking.lot.models.vehicle.VehicleType;

import static com.parking.lot.models.account.UserType.ADMIN;

public class ParkingLotApplication {
    public static void main(String[] args) throws InvalidParkingFloorException {
        ParkingLot parkingLot = ParkingLot.INSTANCE;

        Address address = new Address();
        address.setAddressLine1("Ram parking Complex");
        address.setStreet("BG Road");
        address.setCity("Bangalore");
        address.setState("Karnataka");
        address.setCountry("India");
        address.setPinCode("560075");

        parkingLot.setAddress(address);
        //Admin tests
        User adminAccount = new User(ADMIN);
        //Admin Case 1 - should be able to add parking floor case
        adminAccount.addParkingFloor(new ParkingFloor("1"));
        //Admin Case 2 - should be able to add parking floor case
        adminAccount.addParkingFloor(new ParkingFloor("2"));

        //Admin Case 3 - should be able to add entrance panel
        EntrancePanel entrancePanel = new EntrancePanel("1");
        adminAccount.addEntrancePanel(entrancePanel);

        //Admin Case 4 - should be able to add exit panel
        ExitPanel exitPanel = new ExitPanel("1");
        adminAccount.addExitPanel(exitPanel);

        String floorId = parkingLot.getParkingFloors().get(0).getFloorId();

        ///Admin case 5 - should be able to add car parking spot
        ParkingSpot carSpot1 = new CarParkingSpot("c1");
        adminAccount.addParkingSlotsToFloor(floorId, carSpot1);
        ///Admin case 6 - should be able to add bike parking spot
        ParkingSpot bikeSport = new MotorBikeParkingSpot("b1");
        adminAccount.addParkingSlotsToFloor(floorId, bikeSport);
        ///Admin case 7 - should be able to add car parking spot
        ParkingSpot carSpot2 = new CarParkingSpot("c2");
        adminAccount.addParkingSlotsToFloor(floorId, carSpot2);

        // Test case 1 - check for availability of parking lot - TRUE
        System.out.println(ParkingLot.INSTANCE.canPark(VehicleType.CAR));

        // Test case 2 - check for availability of parking lot - FALSE
        System.out.println(ParkingLot.INSTANCE.canPark(VehicleType.EBIKE));

        // Test case 3 - check for availability of parking lot - FALSE
        System.out.println(ParkingLot.INSTANCE.canPark(VehicleType.ELECTRIC));

        // TEST case 4 - Check if full
        System.out.println(ParkingLot.INSTANCE.isFull());

        // Test case 5 - get parking spot
        Vehicle vehicle = new Car("KA05MR2311");
        ParkingSpot availableSpot = ParkingLot.INSTANCE.getParkingSpot(vehicle.getVehicleType());
        System.out.println(availableSpot.getParkingSpotType());
        System.out.println(availableSpot.getParkingSpotId());

        // Test case 6 - should not be able to get spot
        Vehicle van = new Van("KA01MR7804");
        ParkingSpot vanSpot = ParkingLot.INSTANCE.getParkingSpot(van.getVehicleType());
        System.out.println(null == vanSpot);

        //Test case 7 - Entrance Panel - 1
        System.out.println(ParkingLot.INSTANCE.getEntrancePanels().size());

        // Test case - 8 - Should be able to get parking ticket
        ParkingTicket parkingTicket = entrancePanel.getParkingTicket(vehicle);
        System.out.println(parkingTicket.getAllottedParkingSpotId());

        adminAccount.addParkingSlotsToFloor(floorId, carSpot1);
        // Test case - 9 - Should be able to get parking ticket
        Vehicle car = new Car("KA02MR6355");
        ParkingTicket parkingTicket1 = entrancePanel.getParkingTicket(car);

        // Test case 10 - Should not be able to get ticket
        ParkingTicket tkt = entrancePanel.getParkingTicket(new Car("ka04rb8458"));
        System.out.println(null == tkt);

        // Test case 11 - Should be able to get ticket
        ParkingTicket mtrTkt = entrancePanel.getParkingTicket(new MotorBike("ka01ee4901"));
        System.out.println(mtrTkt.getAllottedParkingSpotId());

        //Test case 12 - vacate parking spot
        mtrTkt = exitPanel.scanAndVacate(mtrTkt);
        System.out.println(mtrTkt.getCharges());
        System.out.println(mtrTkt.getCharges() > 0);

        // Test case 13 - park on vacated spot
        ParkingTicket mtrTkt1 = entrancePanel.getParkingTicket(new MotorBike("ka01ee7791"));
        System.out.println(mtrTkt.getAllottedParkingSpotId());

        // Test case 14 - park when spot is not available
        ParkingTicket unavaialbemTkt =
            entrancePanel.getParkingTicket(new MotorBike("ka01ee4455"));
        System.out.println(null == unavaialbemTkt);

        // Test cast 15 - vacate car
        parkingTicket = exitPanel.scanAndVacate(parkingTicket);
        System.out.println(parkingTicket.getCharges());
        System.out.println(parkingTicket.getCharges() > 0);

        //Test case 16 - Now should be able to park car
        System.out.println(ParkingLot.INSTANCE.canPark(VehicleType.CAR));

        //Test case 17 - Should be able to vacate parked vehicle
        parkingTicket1 = exitPanel.scanAndVacate(parkingTicket1);
        System.out.println(parkingTicket1.getCharges());
        System.out.println(parkingTicket1.getCharges() > 0);

        //Test case 18 - check for slots count
        System.out.println(ParkingLot.INSTANCE.getParkingFloors()
                                              .get(0).getParkingSpots().get(ParkingSpotType.CAR).size());

        //Test case 19 - Payment
        Payment payment = new Payment(UUID.randomUUID().toString(),
                                      parkingTicket1.getParkingTicketId(), parkingTicket1.getCharges());
        payment.makePayment();
        System.out.println(payment.getPaymentStatus());

        //Test case 20 - vacate motorbike spot
        mtrTkt = exitPanel.scanAndVacate(mtrTkt);
        System.out.println(ParkingLot.INSTANCE.getParkingFloors()
                                              .get(0).getParkingSpots().get(ParkingSpotType.MOTORBIKE).size());
        System.out.println(mtrTkt.getCharges());
    }
}
