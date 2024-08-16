package com.ecommerce.plantme.payloads;

import com.ecommerce.plantme.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    Long paymentId;
    User user;
    Double amount;
    String paymentStatus;
    LocalDateTime paymentDate;
}
