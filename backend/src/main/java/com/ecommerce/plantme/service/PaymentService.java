package com.ecommerce.plantme.service;

import com.ecommerce.plantme.payloads.PaymentDTO;

public interface PaymentService {
    PaymentDTO processPayment(Long userId, Double amount, String paymentMethod);
}