package com.ecommerce.plantme.controller;
import com.ecommerce.plantme.payloads.PaymentDTO;
import com.ecommerce.plantme.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentDTO> processPayment(
            @RequestParam Long userId,
            @RequestParam Double amount,
            @RequestParam String paymentMethod
    ) {
        PaymentDTO paymentDTO = paymentService.processPayment(userId, amount, paymentMethod);
        return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
    }
}

