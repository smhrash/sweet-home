package com.upgrad.paymentservice.service;

import com.upgrad.paymentservice.entity.TransactionDetailsEntity;
import com.upgrad.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{


    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public TransactionDetailsEntity saveTransaction(TransactionDetailsEntity transaction) {
        return paymentRepository.save(transaction);
    }

    @Override
    public TransactionDetailsEntity getTransactionDetails(Integer transactionId) {
        return paymentRepository.findById(transactionId).orElse(null);
    }
}
