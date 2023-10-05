package com.upgrad.paymentservice.controller;

import com.upgrad.paymentservice.entity.TransactionDetailsEntity;
import com.upgrad.paymentservice.model.PaymentResponseVO;
import com.upgrad.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.upgrad.paymentservice.constants.PaymentConstants.PAYMENT_SUCCESS;

@RestController
@RequestMapping("/payment")
public class PaymentServiceController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/transaction")
    public ResponseEntity<Integer> initiatePayment(@RequestBody TransactionDetailsEntity transactionDetails) {
        TransactionDetailsEntity savedTransaction = paymentService.saveTransaction(transactionDetails);
        return new ResponseEntity<>(savedTransaction.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<TransactionDetailsEntity> getTransactionDetails(@PathVariable Integer transactionId) {
        TransactionDetailsEntity transaction = paymentService.getTransactionDetails(transactionId);
        if(transaction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping("/payments/status")
    public ResponseEntity<PaymentResponseVO> getPaymentStatus(@RequestParam String orderId) {

        return ResponseEntity.ok(PaymentResponseVO
                .builder()
                .orderId(orderId)
                .paymentStatus(PAYMENT_SUCCESS)
                .paymentReferenceNumber(UUID.randomUUID().toString()) //MOCKING THE RESPONSE
                .build());
    }
}
