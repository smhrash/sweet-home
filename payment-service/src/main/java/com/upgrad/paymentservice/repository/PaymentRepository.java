package com.upgrad.paymentservice.repository;

import com.upgrad.paymentservice.entity.TransactionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<TransactionDetailsEntity, Integer> {
}
