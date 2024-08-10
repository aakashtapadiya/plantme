package com.ecommerce.plantme.factorypattern.productMapping;

import com.ecommerce.plantme.payloads.CategoryProductDTO;

public interface CategoryProductMapper<T> {
    T categoryProductDTOToEntity (CategoryProductDTO dto);
    CategoryProductDTO entityToCategoryProductDTO (T entity);
}
