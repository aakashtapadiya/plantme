package com.ecommerce.plantme.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "payment_status")
    private String paymentStatus; // e.g., "SUCCESS", "FAILED"

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "payment_method")
    private String paymentMethod; // e.g., "CREDIT_CARD", "PAYPAL"
}
