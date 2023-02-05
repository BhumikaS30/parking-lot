package com.parking.lot.models.parking;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import static com.parking.lot.models.parking.PaymentStatus.SUCCESS;

@Getter
public class Payment {

    private final String id;

    private final String ticketId;

    private final double amount;

    @Setter
    private LocalDateTime initiatedDate;

    @Setter
    private LocalDateTime completedDate;

    @Setter
    private PaymentStatus paymentStatus;

    public Payment(String id, String ticketId, double amount) {
        this.id = id;
        this.ticketId = ticketId;
        this.amount = amount;
    }

    public void makePayment() {
        this.initiatedDate = LocalDateTime.now();
        this.completedDate = LocalDateTime.now();
        this.paymentStatus = SUCCESS;
    }
}
