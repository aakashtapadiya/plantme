package com.ecommerce.plantme.implementation;

import com.ecommerce.plantme.entity.Payment;
import com.ecommerce.plantme.entity.Product;
import com.ecommerce.plantme.payloads.PaymentDTO;
import com.ecommerce.plantme.payloads.PaymentDTO;
import com.ecommerce.plantme.repository.PaymentRepo;
import com.ecommerce.plantme.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private ModelMapper modelMapper;

    @Autowired
    public PaymentServiceImpl(PaymentRepo paymentRepo,ModelMapper modelMapper) {
        this.paymentRepo = paymentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentDTO processPayment(Long userId, Double amount, String paymentMethod) {
        // Here you would integrate with a payment gateway to process the payment

        // Simulate a payment processing
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setAmount(amount);
        payment.setPaymentStatus("SUCCESS"); // set this based on actual payment status
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(paymentMethod);

        paymentRepo.save(payment);

        return this.PaymentToDto(payment);
    }

    /**
     * MAPPING PaymentDTO to Product
     * @param PaymentDTO
     * @return Payment
     */
    private Payment dtoToPayment (PaymentDTO PaymentDTO) {
        Payment payment = this.modelMapper.map(PaymentDTO, Payment.class);
        return payment;
    }

    /**
     * MAPPING Product TO PaymentDTO
     * @param payment
     * @return PaymentDTO
     */
    private PaymentDTO PaymentToDto (Payment payment) {
        PaymentDTO PaymentDTO = this.modelMapper.map(payment, PaymentDTO.class);
        return PaymentDTO;
    }
}
