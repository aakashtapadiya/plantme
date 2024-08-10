package com.ecommerce.plantme.controller;

import com.ecommerce.plantme.payloads.CartDTO;
import com.ecommerce.plantme.payloads.UserDTO;
import com.ecommerce.plantme.service.CartService;
import com.ecommerce.plantme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    private CartService cartService;

    @Autowired
    public UserController (UserService userService,CartService cartService) {
        this.userService = userService;
        this.cartService=cartService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> getUser (@PathVariable Long userId) {
        UserDTO userDTO = userService.getUserbyId(userId);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/cart/addCart/{userId}/{productId}")
    public ResponseEntity<CartDTO> addToCart(@PathVariable Long productId, @PathVariable Long userId){
//TOdo:check if user is logged in.if quantity is in stock(if stock is zero change the button and disable from frontend)
        CartDTO cartDTO= cartService.addToCart(productId,userId);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/cart/loadCart/{userId}")
    public ResponseEntity<List<CartDTO>> LoadCartPage(@PathVariable Long userId){
        List<CartDTO> cartDTOS=cartService.findCartByUserId(userId);
        return new ResponseEntity<>(cartDTOS, HttpStatus.FOUND);
    }
    @GetMapping("/cart/getCountAndPrice/{userId}")
    public ResponseEntity<List<Double>> getCountAndPrice(@PathVariable Long userId){
        List<Double> countAndPrice=cartService.getCartCountAndPrice(userId);
        return new ResponseEntity<>(countAndPrice, HttpStatus.FOUND);
    }
    @PutMapping("/cart/updatecart/{userId}/{productId}/{quantity}")
    public  ResponseEntity<CartDTO> updateQuantity(@PathVariable long userId, @PathVariable long productId, @PathVariable int quantity){
        CartDTO cartDTO=cartService.updateQuantity(userId,productId,quantity);
        return new ResponseEntity<>(cartDTO,HttpStatus.OK);
    }

}
