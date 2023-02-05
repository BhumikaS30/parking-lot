package com.parking.lot.models.parking;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingTicket {

    private String parkingTicketId;

    private String licenseNumber;

    private String allottedParkingSpotId;

    private LocalDateTime issuedAt;

    private LocalDateTime vacatedAt;

    private double charges;

    private TicketStatus ticketStatus;
}
