package com.ecommerce.plantme.config;

import com.ecommerce.plantme.entity.categories.Plants;
import com.ecommerce.plantme.payloads.ProductDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    /**
     * Creating Bean for Model Mapper
     *
     * @param
     * @return ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

//        TypeMap<ProductDTO, Plants> typeMap = modelMapper.createTypeMap(ProductDTO.class, Plants.class);
//
//        typeMap.addMappings(mapper -> {
//            // common fields
//            mapper.map(ProductDTO::getProductName, Plants::setProductName);
//            mapper.map(ProductDTO::getProductDescription, Plants::setProductDescription);
//            mapper.map(ProductDTO::getImage, Plants::setImage);
//            mapper.map(ProductDTO::getPrice, Plants::setPrice);
//            mapper.map(ProductDTO::getStockQuantity, Plants::setStockQuantity);
//            mapper.map(ProductDTO::getRating, Plants::setRating);
//            mapper.map(ProductDTO::getCategory, Plants::setCategory);
//            mapper.map(ProductDTO::getTag, Plants::setTag);
//            mapper.map(ProductDTO::getProductStatus, Plants::setProductStatus);
//            // Plant-specific fields from PlantsDTO
//            mapper.map(src -> src.getPlantsDTO().getPlantType(), Plants::setPlantType);
//            mapper.map(src -> src.getPlantsDTO().getLight(), Plants::setLight);
//            mapper.map(src -> src.getPlantsDTO().getPlantLocation(), Plants::setPlantLocation);
//            mapper.map(src -> src.getPlantsDTO().getMaintenance(), Plants::setMaintenance);
//            mapper.map(src -> src.getPlantsDTO().getWaterSchedule(), Plants::setWaterSchedule);
//            mapper.map(src -> src.getPlantsDTO().getPlantColor(), Plants::setPlantColor);
//            mapper.map(src -> src.getPlantsDTO().getPlantSize(), Plants::setPlantSize);
//        });

        return modelMapper;
    }

}
