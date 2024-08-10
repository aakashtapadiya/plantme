package com.ecommerce.plantme.entity;


import com.ecommerce.plantme.entity.enums.Category;
import com.ecommerce.plantme.entity.enums.CommonStatus;
import com.ecommerce.plantme.entity.enums.ProductTag;
import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "Product")
@Inheritance(strategy = InheritanceType.JOINED)

public class Product {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", unique = true, nullable = false)
    private String productName;

    @Column(name = "product_description", unique = true, nullable = false)
    private String productDescription;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "stockQuantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "rating")
    private Double rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag")
    private ProductTag tag;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status")
    private CommonStatus productStatus;

}

