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
    @Column(nullable = true)
    private LocalDateTime fromDate;
    @Column(nullable = true)
    private LocalDateTime toDate;
    @Column( length=15, nullable = true , unique = true)
    private String aadharNumber;
    @Column(nullable = false)
    private int numOfRooms;
    @Column( length=15, nullable = false)
    private  String roomNumbers;
    @Column(nullable = false)
    private int roomPrice;
    @Column(nullable = false)
    private int transactionId;
    @Column(nullable = true)
    private LocalDateTime bookedOn;

}
