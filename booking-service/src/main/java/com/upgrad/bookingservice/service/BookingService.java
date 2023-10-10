package com.upgrad.bookingservice.service;

import com.upgrad.bookingservice.dto.PaymentDTO;
import com.upgrad.bookingservice.dto.PaymentRequest;
import com.upgrad.bookingservice.entity.BookingInfoEntity;
import com.upgrad.bookingservice.dto.PaymentRequest;


public interface BookingService {

    BookingInfoEntity createBooking(BookingInfoEntity booking);

    PaymentDTO processPayment(int bookingId, PaymentRequest paymentRequest);

    BookingInfoEntity getBookingById(int bookingId);
}
