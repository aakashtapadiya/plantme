package com.ecommerce.plantme.service;

import com.ecommerce.plantme.payloads.ProductDTO;

import java.util.List;

public interface WishlistService {

    String addProductToWishlist (Long userId, Long productId);

    List<ProductDTO> getAllProductsFromWishlist (Long userId);

    String removeProductFromWishlist (Long userId, Long productId);
}
