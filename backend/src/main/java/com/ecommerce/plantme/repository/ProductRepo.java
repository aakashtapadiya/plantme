package com.ecommerce.plantme.repository;

import com.ecommerce.plantme.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {
}
