package com.upgrad.bookingservice.controller;

import com.upgrad.bookingservice.dto.ErrorResponse;
import com.upgrad.bookingservice.dto.PaymentDTO;
import com.upgrad.bookingservice.dto.PaymentRequest;
import com.upgrad.bookingservice.entity.BookingInfoEntity;
import com.upgrad.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hotel")
public class BookingController {

    @Autowired
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(value = "/booking", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingInfoEntity> createBooking(@RequestBody BookingInfoEntity booking) {
        BookingInfoEntity savedBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    @PostMapping(value = "/booking/{bookingId}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> processPayment(@PathVariable int bookingId, @RequestBody PaymentRequest paymentRequest) {
        if (!"UPI".equals(paymentRequest.getPaymentMode()) && !"CARD".equals(paymentRequest.getPaymentMode())) {
            return new ResponseEntity<>(new ErrorResponse("Invalid mode of payment", 400), HttpStatus.BAD_REQUEST);
        }

        PaymentDTO transactionResponse = bookingService.processPayment(bookingId, paymentRequest);
        if (transactionResponse == null) {
            return new ResponseEntity<>(new ErrorResponse("Invalid Booking Id", 400), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }


}
