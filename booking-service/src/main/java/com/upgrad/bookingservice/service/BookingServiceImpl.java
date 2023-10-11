package com.upgrad.bookingservice.service;

import com.upgrad.bookingservice.dto.PaymentDTO;
import com.upgrad.bookingservice.dto.PaymentRequest;
import com.upgrad.bookingservice.entity.BookingInfoEntity;
import com.upgrad.bookingservice.repository.BookingRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.temporal.ChronoUnit;
import java.util.*;

import org.slf4j.Logger;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);


    @Value("${paymentService.url}")
    private String paymentServiceUrl;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public BookingInfoEntity createBooking(BookingInfoEntity booking) {
        // Validate if fromDate and toDate are present
        if (booking.getFromDate() == null || booking.getToDate() == null) {
            throw new IllegalArgumentException("Booking dates can't be null");
        }

        try {
            // Get random room numbers
            List<String> roomNumbers = getRandomNumbers(booking.getNumOfRooms());
            booking.setRoomNumbers(String.join(",", roomNumbers));

            // Calculate room price
            long days = ChronoUnit.DAYS.between(booking.getFromDate(), booking.getToDate());
            int roomPrice = 1000 * booking.getNumOfRooms() * (int) days;
            booking.setRoomPrice(roomPrice);

            return bookingRepository.save(booking);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public PaymentDTO processPayment(int bookingId, PaymentRequest paymentRequest) {
        try {
            // Communicate with the Payment Service
            PaymentDTO response = restTemplate.postForObject(paymentServiceUrl, paymentRequest, PaymentDTO.class);

            // Now update the booking information with the transaction ID received from the payment service
            BookingInfoEntity booking = bookingRepository.findById(bookingId).orElse(null);
            if (booking != null && response != null) {
                booking.setTransactionId(response.getTransactionId());
                bookingRepository.save(booking);
            }

            return response; // Return the response from the payment service
        } catch (Exception e) {
            LOGGER.error("Error processing payment for booking ID: {}", bookingId, e);
            throw new RuntimeException("Error processing payment", e);
        }
    }

    @Override
    public BookingInfoEntity getBookingById(int bookingId) {
        Optional<BookingInfoEntity> bookingOptional = bookingRepository.findById(bookingId);
        return bookingOptional.orElse(null);
    }

    private List<String> getRandomNumbers(int count) {
        Random rand = new Random();
        int upperBound = 100;
        List<String> numberList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            numberList.add(String.valueOf(rand.nextInt(upperBound)));
        }
        return numberList;
    }

}
