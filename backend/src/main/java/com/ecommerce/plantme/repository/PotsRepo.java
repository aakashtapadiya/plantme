package com.ecommerce.plantme.repository;

import com.ecommerce.plantme.entity.categories.Pots;
import com.ecommerce.plantme.entity.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PotsRepo extends JpaRepository<Pots, Long> {

    @Query("SELECT p FROM Pots p WHERE p.category = ?1")
    List<Pots> findByCategory(Category category);
}
