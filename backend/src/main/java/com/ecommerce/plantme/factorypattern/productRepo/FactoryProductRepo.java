package com.ecommerce.plantme.factorypattern.productRepo;

import com.ecommerce.plantme.entity.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactoryProductRepo {

    JpaRepository< ?, Long> getRepository(Category category);

}
