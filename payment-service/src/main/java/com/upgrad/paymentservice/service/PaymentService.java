package com.upgrad.paymentservice.service;

import com.upgrad.paymentservice.entity.TransactionDetailsEntity;

public interface PaymentService {


    TransactionDetailsEntity saveTransaction(TransactionDetailsEntity transaction);

    TransactionDetailsEntity getTransactionDetails(Integer transactionId);
}
