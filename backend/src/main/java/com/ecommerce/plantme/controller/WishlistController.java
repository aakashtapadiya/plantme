package com.ecommerce.plantme.controller;

import com.ecommerce.plantme.payloads.ProductDTO;
import com.ecommerce.plantme.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class WishlistController {

    private WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/wishlist/{userId}/{productId}")
    public ResponseEntity<String> addProductToWishlist (@PathVariable Long userId, @PathVariable Long productId) {
        String message = wishlistService.addProductToWishlist(userId, productId);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/wishlist/{userId}")
    public ResponseEntity<List<ProductDTO>> getAllWishllistItems (@PathVariable Long userId){
        List<ProductDTO> productDTOList = wishlistService.getAllProductsFromWishlist(userId);
        return new ResponseEntity<>(productDTOList, HttpStatus.FOUND);
    }

    @DeleteMapping("/wishlist/{userId}/{productId}")
    public ResponseEntity<String> deleteItemFromWishlist (@PathVariable Long userId, @PathVariable Long productId) {
        String message = wishlistService.removeProductFromWishlist(userId, productId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
