package com.ecommerce.plantme.implementation;

import com.ecommerce.plantme.entity.Product;
import com.ecommerce.plantme.entity.enums.Category;
import com.ecommerce.plantme.exceptions.ResourceAlreadyFoundException;
import com.ecommerce.plantme.exceptions.ResourceNotFoundException;
import com.ecommerce.plantme.factorypattern.productMapping.CategoryProductFactory;
import com.ecommerce.plantme.factorypattern.productMapping.CategoryProductMapper;
import com.ecommerce.plantme.payloads.CategoryProductDTO;
import com.ecommerce.plantme.payloads.ProductDTO;
import com.ecommerce.plantme.factorypattern.productRepo.FactoryProductRepo;
import com.ecommerce.plantme.repository.ProductRepo;
import com.ecommerce.plantme.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepo productRepo;

    private FactoryProductRepo factoryProductRepo;

    private CategoryProductFactory categoryProductFactory;


    private ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl (
            ProductRepo productRepo,
            FactoryProductRepo factoryProductRepo,
            CategoryProductFactory categoryProductFactory,
            ModelMapper modelMapper
    ) {
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
        this.categoryProductFactory = categoryProductFactory;
        this.factoryProductRepo = factoryProductRepo;
    }

    public <T> JpaRepository<T, Long> repository(Category category) {
        return (JpaRepository<T, Long>) factoryProductRepo.getRepository(category);
    }


    @Transactional
    @Override
    public CategoryProductDTO addProduct(CategoryProductDTO categoryProductDTO) {

        ProductDTO productDTO = categoryProductDTO.getProductDTO();
        if (productRepo.findByName(productDTO.getProductName()) != null){
            throw new ResourceAlreadyFoundException("Product", "Product Name", productDTO.getProductName());
        } if (productRepo.findByDescription(productDTO.getProductDescription()) != null) {
            throw new ResourceAlreadyFoundException("Product", "Product Description", productDTO.getProductDescription());
        }
        Category category = productDTO.getCategory();
        //get mapper as per category
        CategoryProductMapper<Object> mapper = categoryProductFactory.getMapper(category);

        //convert categoryProductDTO to entity
        Object entity = mapper.categoryProductDTOToEntity(categoryProductDTO);

        //save entity (e.g plants.save)
        repository(category).save(entity);

        //return categoryProductDTO
        return mapper.entityToCategoryProductDTO(entity);
    }



    @Override
    public CategoryProductDTO getProductById(Category category, Long productId) {
        Object entityDB = repository(category).findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));
        CategoryProductMapper<Object> mapper = categoryProductFactory.getMapper(category);
        return mapper.entityToCategoryProductDTO(entityDB);
    }

    @Override
    public List<CategoryProductDTO> getAllProductByCategory(Category category) {
        List<Object> list = repository(category).findAll();
        if (list.isEmpty()) {
            throw new ResourceNotFoundException(category.toString());
        }
        List<CategoryProductDTO> categoryProductDTOList = new ArrayList<>();
        CategoryProductMapper<Object> mapper = categoryProductFactory.getMapper(category);
        for (Object object : list) {
            categoryProductDTOList.add(mapper.entityToCategoryProductDTO(object));
        }
        return categoryProductDTOList;
    }

    @Transactional
    @Override
    public CategoryProductDTO updateProduct (CategoryProductDTO categoryProductDTO, Long productId) {

        Category category = categoryProductDTO.getProductDTO().getCategory();

        Object entityDB = repository(category).findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

        String name = categoryProductDTO.getProductDTO().getProductName();
        String desc = categoryProductDTO.getProductDTO().getProductDescription();

        if (productRepo.findByName(name) != null){
            throw new ResourceAlreadyFoundException("Product", "Product Name", name);
        } if (productRepo.findByDescription(desc) != null) {
            throw new ResourceAlreadyFoundException("Product", "Product Description", desc);
        }
        //set ProductId to execute Update and not create
        categoryProductDTO.getProductDTO().setProductId(productId);

        CategoryProductMapper<Object> mapper = categoryProductFactory.getMapper(category);
        Object entityToSave = mapper.categoryProductDTOToEntity(categoryProductDTO);
        repository(category).save(entityToSave);
        return mapper.entityToCategoryProductDTO(entityToSave);
    }

    @Transactional
    @Override
    public ProductDTO updateStockQuantity(Integer stockQuantity, Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));
        product.setStockQuantity(stockQuantity);
        productRepo.save(product);
        return this.ProductToDto(product);
    }

    @Override
    public String deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));
        productRepo.deleteById(productId);
        return "Deleted";
    }


    /**
     * MAPPING ProductDTO to Product
     * @param productDTO
     * @return Product
     */
    private Product dtoToProduct (ProductDTO productDTO) {
        Product product = this.modelMapper.map(productDTO, Product.class);
        return product;
    }

    /**
     * MAPPING Product TO ProductDTO
     * @param product
     * @return ProductDTO
     */
    private ProductDTO ProductToDto (Product product) {
        ProductDTO ProductDTO = this.modelMapper.map(product, ProductDTO.class);
        return ProductDTO;
    }
}
