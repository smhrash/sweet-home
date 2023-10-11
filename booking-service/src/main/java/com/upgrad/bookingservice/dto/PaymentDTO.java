package com.upgrad.bookingservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private int transactionId;
    private String status;

    public PaymentDTO(int transactionId) {
        this.transactionId = transactionId;
    }
}

