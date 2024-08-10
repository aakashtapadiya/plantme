package com.ecommerce.plantme.factorypattern.productMapping;

import com.ecommerce.plantme.entity.categories.Plants;
import com.ecommerce.plantme.payloads.CategoryProductDTO;
import com.ecommerce.plantme.payloads.PlantsDTO;
import com.ecommerce.plantme.payloads.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlantMapper implements CategoryProductMapper<Plants> {

    private final ModelMapper modelMapper;

    @Autowired
    public PlantMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Plants categoryProductDTOToEntity (CategoryProductDTO dto) {
        Plants plants = modelMapper.map(dto.getProductDTO(), Plants.class);
        PlantsDTO plantsDTO = dto.getPlantsDTO();
        plants.setPlantType(plantsDTO.getPlantType());
        plants.setLight(plantsDTO.getLight());
        plants.setPlantLocation(plantsDTO.getPlantLocation());
        plants.setMaintenance(plantsDTO.getMaintenance());
        plants.setWaterSchedule(plantsDTO.getWaterSchedule());
        plants.setPlantColor(plantsDTO.getPlantColor());
        plants.setPlantSize(plantsDTO.getPlantSize());
        return plants;
    }

    @Override
    public CategoryProductDTO entityToCategoryProductDTO (Plants plants) {
        PlantsDTO plantsDTO = modelMapper.map(plants, PlantsDTO.class);
        ProductDTO productDTO = modelMapper.map(plants, ProductDTO.class);
        CategoryProductDTO categoryProductDTO = new CategoryProductDTO();
        categoryProductDTO.setProductDTO(productDTO);
        categoryProductDTO.setPlantsDTO(plantsDTO);
        return categoryProductDTO;
    }
}
