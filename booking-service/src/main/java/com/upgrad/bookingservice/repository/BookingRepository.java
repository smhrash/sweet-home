package com.upgrad.bookingservice.repository;

import com.upgrad.bookingservice.entity.BookingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingInfoEntity, Integer> {
}
