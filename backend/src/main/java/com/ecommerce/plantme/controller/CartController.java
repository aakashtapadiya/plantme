package com.ecommerce.plantme.controller;

import com.ecommerce.plantme.payloads.CartDTO;
import com.ecommerce.plantme.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController (CartService cartService) {
        this.cartService=cartService;
    }

    @PostMapping("/addCart/{userId}/{productId}")
    public ResponseEntity<CartDTO> addToCartItem(@PathVariable Long productId, @PathVariable Long userId){
    //TOdo:check if user is logged in.if quantity is in stock(if stock is zero change the button and disable from frontend)
        CartDTO cartDTO= cartService.addToCart(productId,userId);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/loadCart/{userId}")
    public ResponseEntity<List<CartDTO>> LoadCartPage(@PathVariable Long userId){
        List<CartDTO> cartDTOS=cartService.findCartByUserId(userId);
        return new ResponseEntity<>(cartDTOS, HttpStatus.FOUND);
    }

    @PutMapping("/updateCart/{userId}/{productId}/{quantity}")
    public  ResponseEntity<CartDTO> updateQuantity(@PathVariable long userId, @PathVariable long productId, @PathVariable int quantity){
        CartDTO cartDTO=cartService.updateQuantity(userId,productId,quantity);
        return new ResponseEntity<>(cartDTO,HttpStatus.OK);
    }
    @DeleteMapping("/deleteCart/{cartId}")
    public ResponseEntity<String> deleteCartItem (@PathVariable Long cartId) {
        String status = cartService.deletecartItemForUser(cartId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
