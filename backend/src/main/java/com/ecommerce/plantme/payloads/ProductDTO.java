package com.ecommerce.plantme.payloads;

import com.ecommerce.plantme.entity.enums.Category;
import com.ecommerce.plantme.entity.enums.CommonStatus;
import com.ecommerce.plantme.entity.enums.ProductTag;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long productId;
    private String productName;
    private String productDescription;
    private String image;
    private Double price;
    private Integer stockQuantity;
    private Double rating;
    private Category category;
    private ProductTag tag;
    private CommonStatus productStatus = CommonStatus.ACTIVE;
}
