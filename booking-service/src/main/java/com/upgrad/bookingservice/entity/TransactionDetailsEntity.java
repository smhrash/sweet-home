package com.upgrad.bookingservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class TransactionDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String paymentMode;

    @Column(nullable = false)
    private Integer bookingId;

    @Column(nullable = true)
    private String upiId;

    @Column(nullable = true)
    private String cardNumber;
}
