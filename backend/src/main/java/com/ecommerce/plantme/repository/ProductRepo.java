package com.ecommerce.plantme.repository;

import com.ecommerce.plantme.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.productName = ?1")
    Product findByName (String productName);

    @Query("SELECT p FROM Product p WHERE p.productDescription = ?1")
    Product findByDescription (String productDescription);

    @Query("SELECT p FROM Product p WHERE p.image = ?1")
    Product findByImage (String image);

}
