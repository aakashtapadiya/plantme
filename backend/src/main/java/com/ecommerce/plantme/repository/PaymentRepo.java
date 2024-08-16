package com.ecommerce.plantme.repository;

import com.ecommerce.plantme.entity.Payment;
import com.ecommerce.plantme.entity.User;
import com.ecommerce.plantme.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p  WHERE p.user.userId = ?1")
    List<Payment> findPaymentsByUserId(Long userId);


}