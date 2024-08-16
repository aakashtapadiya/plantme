package com.ecommerce.plantme.service;

import com.ecommerce.plantme.payloads.CartDTO;

import java.util.List;

public interface CartService {

    public CartDTO addToCart(Long productId, Long userId);

    public List<CartDTO> findCartByUserId(Long userId);

    CartDTO updateQuantity(long userId, long productId, int quantity);

    String deletecartItemForUser(Long cartItemId);
}
