package com.ecommerce.plantme.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProductDTO {

    private ProductDTO productDTO;
    private PlantsDTO plantsDTO;
    private PotsDTO potsDTO;

}
