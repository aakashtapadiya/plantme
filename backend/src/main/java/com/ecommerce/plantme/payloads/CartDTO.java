package com.ecommerce.plantme.payloads;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class CartDTO {
        private Long CartId;
        private Long userId;
        private Long productId;
        private Integer quantity;
        private Double price;
    }

