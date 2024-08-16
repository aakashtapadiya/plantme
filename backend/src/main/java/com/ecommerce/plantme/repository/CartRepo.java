package com.ecommerce.plantme.repository;

import com.ecommerce.plantme.entity.CartItem;
import com.ecommerce.plantme.entity.Product;
import com.ecommerce.plantme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<CartItem, Long> {

    CartItem findByUserIdAndProductId(Long userId,Long productId);

    List<CartItem> findByUserId(Long userId);
}