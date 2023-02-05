package com.parking.lot.models.account;

import java.util.Optional;

import com.parking.lot.exceptions.InvalidParkingFloorException;
import com.parking.lot.models.parking.EntrancePanel;
import com.parking.lot.models.parking.ExitPanel;
import com.parking.lot.models.parking.ParkingFloor;
import com.parking.lot.models.parking.ParkingLot;
import com.parking.lot.models.parking.ParkingSpot;

import lombok.Getter;
import lombok.Setter;

import static com.parking.lot.models.account.UserType.ADMIN;
import static java.util.UUID.randomUUID;

@Getter
@Setter
public class User {

    private String id;

    private Contact contact;

    private Address address;

    private PersonalInfo userDetails;

    private UserType userType;

    public User(UserType userType) {
        this.id = randomUUID().toString();
        this.userType = userType;
    }

    public void addParkingFloor(ParkingFloor parkingFloor) {
        if (!ADMIN.equals(userType)) {
            return;
        }
        Optional<ParkingFloor> optionalParkingFloor =
            ParkingLot.INSTANCE.getParkingFloors().stream()
                               .filter(parkingFloor1 -> parkingFloor.getFloorId()
                                                                    .equalsIgnoreCase(parkingFloor1.getFloorId()))
                               .findFirst();
        if (optionalParkingFloor.isPresent()) {
            return;
        }
        ParkingLot.INSTANCE.getParkingFloors().add(parkingFloor);
    }

    public void addParkingSlotsToFloor(String parkingFloorId,
                                       ParkingSpot parkingSpot) throws InvalidParkingFloorException {
        if (!ADMIN.equals(userType)) {
            return;
        }
        Optional<ParkingFloor> optionalParkingFloor = ParkingLot.INSTANCE.getParkingFloors().stream()
                                                                         .filter(parkingFloor -> parkingFloor.getFloorId()
                                                                                                             .equalsIgnoreCase(
                                                                                                                 parkingFloorId))
                                                                         .findFirst();
        if (optionalParkingFloor.isEmpty()) {
            throw new InvalidParkingFloorException("Invalid floor!");
        }
        Optional<ParkingSpot> parkingSpotOptional = optionalParkingFloor.get()
                                                                        .getParkingSpots()
                                                                        .get(parkingSpot.getParkingSpotType())
                                                                        .stream()
                                                                        .filter(ps -> ps.getParkingSpotId()
                                                                                        .equalsIgnoreCase(parkingSpot.getParkingSpotId()))
                                                                        .findFirst();
        if (parkingSpotOptional.isPresent()) {
            return;
        }
        optionalParkingFloor.get().getParkingSpots().get(parkingSpot.getParkingSpotType()).addLast(parkingSpot);
    }

    public void addEntrancePanel(EntrancePanel entrancePanel) {
        if (!ADMIN.equals(userType)) {
            return;
        }
        Optional<EntrancePanel> optionalEntrancePanel =
            ParkingLot.INSTANCE.getEntrancePanels()
                               .stream()
                               .filter(entrancePanel1 -> entrancePanel1.getId()
                                                                       .equalsIgnoreCase(
                                                                           entrancePanel.getId()))
                               .findFirst();

        if (optionalEntrancePanel.isPresent()) {
            return;
        }
        ParkingLot.INSTANCE.getEntrancePanels().add(entrancePanel);
    }

    public void addExitPanel(ExitPanel exitPanel) {
        if (!ADMIN.equals(userType)) {
            return;
        }
        Optional<ExitPanel> optionalExitPanel =
            ParkingLot.INSTANCE.getExitPanels()
                               .stream()
                               .filter(exitPanel1 -> exitPanel1.getId()
                                                               .equalsIgnoreCase(
                                                                   exitPanel.getId()))
                               .findFirst();

        if (optionalExitPanel.isPresent()) {
            return;
        }
        ParkingLot.INSTANCE.getExitPanels().add(exitPanel);
    }
}
