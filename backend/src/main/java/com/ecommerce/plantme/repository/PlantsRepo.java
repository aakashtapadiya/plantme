package com.ecommerce.plantme.repository;

import com.ecommerce.plantme.entity.categories.Plants;
import com.ecommerce.plantme.entity.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface PlantsRepo extends JpaRepository<Plants, Long> {


}
