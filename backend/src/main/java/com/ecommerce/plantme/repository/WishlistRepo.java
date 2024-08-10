package com.ecommerce.plantme.repository;

import com.ecommerce.plantme.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepo extends JpaRepository<Wishlist, Long> {

    @Query("SELECT w FROM Wishlist w JOIN w.product p WHERE w.user.userId = ?1 AND p.productId = ?2")
    Wishlist findWishlistItemByUserIdProductId (Long userId, Long productId);

}
