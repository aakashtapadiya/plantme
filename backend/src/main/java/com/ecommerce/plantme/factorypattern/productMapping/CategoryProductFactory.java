package com.ecommerce.plantme.factorypattern.productMapping;

import com.ecommerce.plantme.entity.enums.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryProductFactory {

    private final PlantMapper plantMapper;
    private final PotMapper potMapper;

    @Autowired
    public CategoryProductFactory(PlantMapper plantMapper, PotMapper potMapper) {
        this.plantMapper = plantMapper;
        this.potMapper = potMapper;
    }

    public <T> CategoryProductMapper<T> getMapper(Category category) {
        switch (category) {
            case PLANTS:
                return (CategoryProductMapper<T>) plantMapper;
            case POTS:
                return (CategoryProductMapper<T>) potMapper;
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }
}
