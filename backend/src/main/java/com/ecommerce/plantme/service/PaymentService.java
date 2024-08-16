package com.ecommerce.plantme.service;

import com.ecommerce.plantme.payloads.PaymentDTO;

import java.util.List;

public interface PaymentService {
    public PaymentDTO processPayment(Long userId, String paymentMethod);
    public List<PaymentDTO> getPaymentsByUserId(Long userId);

}