package com.ecommerce.plantme.repository;

import com.ecommerce.plantme.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
