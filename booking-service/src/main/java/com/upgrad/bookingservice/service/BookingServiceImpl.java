package com.upgrad.bookingservice.service;

import com.upgrad.bookingservice.dto.PaymentDTO;
import com.upgrad.bookingservice.dto.PaymentRequest;
import com.upgrad.bookingservice.entity.BookingInfoEntity;
import com.upgrad.bookingservice.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.upgrad.bookingservice.dto.PaymentRequest;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public BookingInfoEntity createBooking(BookingInfoEntity booking) {
        List<String> roomNumbers = getRandomNumbers(booking.getNumOfRooms());
        booking.setRoomNumbers(String.join(",", roomNumbers));
        int roomPrice = 1000 * booking.getNumOfRooms() * (int) ChronoUnit.DAYS.between(booking.getFromDate(), booking.getToDate());
        booking.setRoomPrice(roomPrice);
        return bookingRepository.save(booking);
    }

    @Override
    public PaymentDTO processPayment(int bookingId, PaymentRequest paymentRequest) {

        String paymentServiceUrl = "http://localhost:8083/payment/transaction";

        // Communicate with the Payment Service
        PaymentDTO response = restTemplate.postForObject(paymentServiceUrl, paymentRequest, PaymentDTO.class);

        // Now update the booking information with the transaction ID received from the payment service
        BookingInfoEntity booking = bookingRepository.findById(bookingId).orElse(null);
        if(booking != null && response != null) {
            booking.setTransactionId(response.getTransactionId());
            bookingRepository.save(booking);
        }

        return response; // Return the response from the payment service
    }

    private List<String> getRandomNumbers(int count){
        Random rand = new Random();
        int upperBound = 100;
        List<String> numberList = new ArrayList<>();
        for (int i=0; i<count; i++){
            numberList.add(String.valueOf(rand.nextInt(upperBound)));
        }
        return numberList;
    }

}
