package com.ecommerce.plantme.repository;

import com.ecommerce.plantme.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {


    @Query("Select c from Cart c where c.userId=?1 and c.productId=?2")
    Cart findByUserIdAndProductId(Long userId,Long productId);

    Integer countByUserId(Long userId);

    List<Cart> findByUserId(Long userId);
}
