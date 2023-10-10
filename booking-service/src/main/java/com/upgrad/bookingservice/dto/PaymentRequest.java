package com.upgrad.bookingservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    private int bookingId;
    private String paymentMode;
    private String upiId;
    private String cardNumber;

}
