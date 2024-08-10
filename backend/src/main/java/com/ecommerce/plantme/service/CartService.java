package com.ecommerce.plantme.service;

import com.ecommerce.plantme.entity.Cart;
import com.ecommerce.plantme.payloads.CartDTO;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface CartService {

    public CartDTO addToCart(Long productId, Long userId);

    public List<Double> getCartCountAndPrice(Long userId);

    public List<CartDTO> findCartByUserId(Long userId);

    CartDTO updateQuantity(long userId, long productId, int quantity);
}
