package com.ecommerce.paymentservice.service;


import com.ecommerce.paymentservice.entity.TransactionDetails;
import com.ecommerce.paymentservice.model.PaymentMode;
import com.ecommerce.paymentservice.model.PaymentRequest;
import com.ecommerce.paymentservice.model.PaymentResponse;
import com.ecommerce.paymentservice.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording payment details : {}",paymentRequest);

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .orderId(paymentRequest.getOrderId())
                .paymentStatus("SUCCESS")
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        transactionDetailsRepository.save(transactionDetails);
        log.info("Transaction Completed with Id: {}", transactionDetails.getId());



        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(long orderId) {
        log.info("Getting payment details for the Order Id : {}",orderId);

         TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(orderId);

         PaymentResponse paymentResponse = PaymentResponse.builder()
                 .paymentId(transactionDetails.getId())
                 .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                 .paymentDate(transactionDetails.getPaymentDate())
                 .status(transactionDetails.getPaymentStatus())
                 .amount(transactionDetails.getAmount())
                 .build();
        return paymentResponse;
    }

    @Override
    public void deletePaymentById(long paymentId) {
        if(transactionDetailsRepository.existsById(paymentId)){
            transactionDetailsRepository.deleteById(paymentId);
        }else {
            throw new RuntimeException("Payment not found with given Id :"+paymentId);
        }
    }
}
