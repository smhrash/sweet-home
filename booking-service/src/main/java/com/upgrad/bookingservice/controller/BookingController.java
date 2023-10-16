package com.upgrad.bookingservice.controller;

import com.upgrad.bookingservice.dto.BookingDTO;
import com.upgrad.bookingservice.dto.ErrorResponse;
import com.upgrad.bookingservice.dto.PaymentDTO;
import com.upgrad.bookingservice.dto.PaymentRequest;
import com.upgrad.bookingservice.entity.BookingInfoEntity;
import com.upgrad.bookingservice.service.BookingService;
import com.upgrad.bookingservice.service.BookingServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping(value = "/hotel")
public class BookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    private BookingService bookingService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/booking", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        bookingDTO.setBookedOn(LocalDateTime.now()); // Set the current date-time
        BookingInfoEntity newBooking = modelMapper.map(bookingDTO, BookingInfoEntity.class);
        BookingInfoEntity savedBooking = bookingService.createBooking(newBooking);
        BookingDTO savedBookingDTO = modelMapper.map(savedBooking, BookingDTO.class);

        return new ResponseEntity<>(savedBookingDTO, HttpStatus.CREATED);
    }

    @PostMapping(value = "/booking/{bookingId}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> processPayment(@PathVariable(name = "bookingId") int bookingId, @RequestBody PaymentRequest paymentRequest) {

        if (!"UPI".equals(paymentRequest.getPaymentMode()) && !"CARD".equals(paymentRequest.getPaymentMode())) {
            return new ResponseEntity<>(new ErrorResponse("Invalid mode of payment", 400), HttpStatus.BAD_REQUEST);
        }
        try {
            BookingInfoEntity updatedBooking = bookingService.getBookingById(bookingId);
            if (updatedBooking == null || paymentRequest.getBookingId() != bookingId) {
                LOGGER.warn("No booking found with ID: {}", bookingId);
                return new ResponseEntity<>(new ErrorResponse("Invalid Booking Id", 400), HttpStatus.BAD_REQUEST);
            }

            PaymentDTO transactionResponse = bookingService.processPayment(bookingId, paymentRequest);
            if (transactionResponse == null) {
                return new ResponseEntity<>(new ErrorResponse("Invalid Booking Id", 400), HttpStatus.BAD_REQUEST);
            }

            BookingDTO savedBookingDTO = modelMapper.map(updatedBooking, BookingDTO.class);
            return new ResponseEntity<>(savedBookingDTO, HttpStatus.CREATED);

        } catch (Exception e) {
            LOGGER.error("Error processing payment for booking ID: {}", bookingId, e);
            throw e; // rethrow the exception to be handled by the controller's global exception handler
        }
    }
}
