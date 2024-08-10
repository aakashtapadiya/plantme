package com.ecommerce.plantme.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CartId;
    private Long userId;
    private Long productId;
    private Integer quantity;
    @Transient
    private Double price;

}
