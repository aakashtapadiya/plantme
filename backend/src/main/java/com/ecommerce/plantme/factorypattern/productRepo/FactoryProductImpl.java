package com.ecommerce.plantme.factorypattern.productRepo;

import com.ecommerce.plantme.entity.enums.Category;
import com.ecommerce.plantme.repository.PlantsRepo;
import com.ecommerce.plantme.repository.PotsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FactoryProductImpl implements FactoryProductRepo {

    private PlantsRepo plantsRepo;

    private PotsRepo potsRepo;

    private static final Map<Category , FactoryProductRepo> productHandler = new HashMap<Category, FactoryProductRepo>();

    @Autowired
    public FactoryProductImpl(
            PlantsRepo plantsRepo,
            PotsRepo potsRepo
    ) {
        this.plantsRepo = plantsRepo;
        this.potsRepo = potsRepo;
    }

    @Override
    public JpaRepository<?, Long> getRepository(Category category) {
        switch (category) {
            case PLANTS:
                return plantsRepo;
            case POTS:
                return potsRepo;
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }

}
