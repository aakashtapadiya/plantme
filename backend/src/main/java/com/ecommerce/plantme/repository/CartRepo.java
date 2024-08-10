package com.ecommerce.plantme.repository;

import com.ecommerce.plantme.entity.Cart;
import com.ecommerce.plantme.entity.Product;
import com.ecommerce.plantme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.user = ?1 AND c.product = ?2")
    Cart findByUserIdAndProductId(User user, Product product);

    Integer countByUser_UserId(Long userId);

    List<Cart> findByUser_UserId(Long userId);
}