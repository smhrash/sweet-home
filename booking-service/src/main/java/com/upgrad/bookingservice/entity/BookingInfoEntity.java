package com.upgrad.bookingservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class BookingInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    @Column(nullable = false)
    private LocalDateTime fromDate;
    @Column(nullable = false)
    private LocalDateTime toDate;
    @Column(length = 20)
    private String aadharNumber;
    @Column(nullable = false)
    private int numOfRooms;
    @Column(length = 10)
    private String roomNumbers;
    private int roomPrice;
    private int transactionId;
    private LocalDateTime bookedOn;

}
