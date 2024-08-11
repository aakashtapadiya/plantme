package com.ecommerce.plantme.controller;

import com.ecommerce.plantme.entity.enums.Category;
import com.ecommerce.plantme.payloads.CategoryProductDTO;
import com.ecommerce.plantme.payloads.ProductDTO;
import com.ecommerce.plantme.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<CategoryProductDTO> addProduct (@RequestBody CategoryProductDTO categoryProductDTO) {
        CategoryProductDTO savedProductDTO = productService.addProduct(categoryProductDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @GetMapping("/product/{category}")
    public ResponseEntity<List<CategoryProductDTO>> getAllProductsByCategory(@PathVariable Category category){
        List<CategoryProductDTO> savedProductDTOList = productService.getAllProductByCategory(category);
        return new ResponseEntity<>(savedProductDTOList, HttpStatus.FOUND);
    }

    @GetMapping("/product/{category}/{productId}")
    public ResponseEntity<CategoryProductDTO> getProductById (@PathVariable Category category, @PathVariable Long productId) {
        CategoryProductDTO categoryProductDTO = productService.getProductById(category, productId);
        return new ResponseEntity<>(categoryProductDTO, HttpStatus.FOUND);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<CategoryProductDTO> updateProductDTO (@RequestBody CategoryProductDTO categoryProductDTO, @PathVariable Long productId){
        CategoryProductDTO updatedCategoryProductDTO = productService.updateProduct(categoryProductDTO, productId);
        return new ResponseEntity<>(updatedCategoryProductDTO, HttpStatus.OK);
    }

    @PutMapping("/product/{productId}/{stockQuantity}")
    public ResponseEntity<ProductDTO> updateStockQuantity (@PathVariable Long productId, @PathVariable Integer stockQuantity) {
        ProductDTO savedProductDTO = productService.updateStockQuantity(stockQuantity, productId);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> deleteProduct (@PathVariable Long productId) {
        return new ResponseEntity<>("msg", HttpStatus.OK);
    }

}
