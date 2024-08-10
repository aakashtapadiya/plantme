package com.ecommerce.plantme.payloads;

import java.time.LocalDateTime;

public class PaymentDTO {
    Long paymentId;
    Long userId;
    Double amount;
    String paymentStatus;
    LocalDateTime paymentDate;
    String paymentMethod;

}
