package com.ecommerce.paymentservice.service;


import com.ecommerce.paymentservice.model.PaymentRequest;
import com.ecommerce.paymentservice.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);

    void deletePaymentById(long paymentId);
}
