package com.ecommerce.plantme.factorypattern.productMapping;

import com.ecommerce.plantme.entity.categories.Pots;
import com.ecommerce.plantme.payloads.CategoryProductDTO;
import com.ecommerce.plantme.payloads.PotsDTO;
import com.ecommerce.plantme.payloads.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PotMapper implements CategoryProductMapper<Pots> {

    private final ModelMapper modelMapper;

    @Autowired
    public PotMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Pots categoryProductDTOToEntity (CategoryProductDTO dto) {
        Pots pots = modelMapper.map(dto.getProductDTO(), Pots.class);
        PotsDTO potsDTO = dto.getPotsDTO();
        pots.setPotSize(potsDTO.getPotSize());
        pots.setPotType(potsDTO.getPotType());
        return pots;
    }

    @Override
    public CategoryProductDTO entityToCategoryProductDTO (Pots pots) {
        PotsDTO potsDTO = modelMapper.map(pots, PotsDTO.class);
        ProductDTO productDTO = modelMapper.map(pots, ProductDTO.class);
        CategoryProductDTO categoryProductDTO = new CategoryProductDTO();
        categoryProductDTO.setProductDTO(productDTO);
        categoryProductDTO.setPotsDTO(potsDTO);
        return categoryProductDTO;
    }
}

