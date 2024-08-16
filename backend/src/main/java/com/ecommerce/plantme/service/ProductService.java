package com.ecommerce.plantme.service;

import com.ecommerce.plantme.entity.enums.Category;
import com.ecommerce.plantme.payloads.CategoryProductDTO;
import com.ecommerce.plantme.payloads.ProductDTO;
import java.util.List;

public interface ProductService {

    CategoryProductDTO addProduct(CategoryProductDTO categoryProductDTO);

    CategoryProductDTO getProductById (Category category, Long productId);

    List<CategoryProductDTO> getAllProductByCategory (Category category);

    CategoryProductDTO updateProduct (CategoryProductDTO categoryProductDTO, Long productId);

    ProductDTO updateStockQuantity (Integer stockQuantity, Long productId);

    String deleteProduct(Long productId);
}
