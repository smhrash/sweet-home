package com.upgrad.bookingservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDTO {

    private int bookingId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String aadharNumber;
    private int numOfRooms;
    private String roomNumbers;
    private int roomPrice;
    private int transactionId;
    private LocalDateTime bookedOn;
}
