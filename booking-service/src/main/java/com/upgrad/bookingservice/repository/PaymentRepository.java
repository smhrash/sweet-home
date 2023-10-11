package com.upgrad.bookingservice.repository;

import com.upgrad.bookingservice.entity.TransactionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<TransactionDetailsEntity, Integer> {
}
