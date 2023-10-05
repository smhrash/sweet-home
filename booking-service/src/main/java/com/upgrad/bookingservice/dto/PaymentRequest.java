package com.upgrad.bookingservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    private String paymentMode;
    private String upiId;
    private String cardNumber;

}
