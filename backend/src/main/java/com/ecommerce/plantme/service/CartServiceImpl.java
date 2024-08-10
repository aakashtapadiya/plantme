package com.ecommerce.plantme.service;

import com.ecommerce.plantme.entity.*;
import com.ecommerce.plantme.entity.Cart;
import com.ecommerce.plantme.exceptions.ResourceNotFoundException;
import com.ecommerce.plantme.payloads.CartDTO;
import com.ecommerce.plantme.repository.CartRepo;
import com.ecommerce.plantme.repository.ProductRepo;
import com.ecommerce.plantme.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;


    @Autowired
    public CartServiceImpl (
            CartRepo cartRepo,
            ProductRepo productRepo,
            UserRepo userRepo,
            ModelMapper modelMapper
    ) {
        this.cartRepo = cartRepo;
        this.productRepo=productRepo;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CartDTO addToCart(Long productId, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
        Product product = productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
        Cart cartItem=cartRepo.findByUserIdAndProductId(userId,productId);
        Cart cart=null;
        //If product already exists change quantity else add new item in cart.
        if (ObjectUtils.isEmpty(cartItem)){
            cart=new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(1);
            cart.setPrice(1*product.getPrice());
        }
        else{
            cart=cartItem;
            cart.setQuantity(cartItem.getQuantity()+1);
            cart.setPrice(cart.getQuantity()*product.getPrice());
        }
        cartRepo.save(cart);
        return this.cartToDto(cart);
    }

    @Override
    public List<Double> getCartCountAndPrice(Long userId) {
        ArrayList<Double> countAndPrice=new ArrayList<>();
        Double count= Double.valueOf(cartRepo.countByUserId(userId));
        List<Cart> carts=cartRepo.findByUserId(userId);
        Double totalPrice=0.0;
        for(Cart c:carts){
            Product p=productRepo.findById(c.getProductId()).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", c.getProductId()));
            totalPrice+=(p.getPrice()*c.getQuantity());
        }
        countAndPrice.add(count);
        countAndPrice.add(totalPrice);
        //return list of total count and price for UI and payment purpose.
        return countAndPrice;
    }

    @Override
    public List<CartDTO> findCartByUserId(Long userId) {
        //Loads entire List of Cart for user
        List<Cart> carts=cartRepo.findByUserId(userId);
        List<CartDTO> cartDTOS = new ArrayList<>();
        for (Cart c : carts) {
            Product p=productRepo.findById(c.getProductId()).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", c.getProductId()));
            c.setPrice(p.getPrice()*c.getQuantity());
            cartDTOS.add(this.cartToDto(c));
        }
        return cartDTOS;
    }

    @Override
    public CartDTO updateQuantity(long userId, long productId, int quantity) {
        Cart c = cartRepo.findByUserIdAndProductId(userId, productId);
        if (c == null) {
            throw new ResourceNotFoundException("Cart", "cartId", String.valueOf(c));
        }
        if (quantity == 0) {
            cartRepo.deleteById(c.getCartId());
            return null;
        } else {
            Product p=productRepo.findById(c.getProductId()).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", c.getProductId()));
            c.setQuantity(quantity);
            c.setPrice(quantity*p.getPrice());
            cartRepo.save(c); // Save the updated cart
        }

        return cartToDto(c);
    }

    /**
     * MAPPING CartDTO to Cart
     * @param cartDTO
     * @return Cart
     */
    private Cart dtoToCart (CartDTO cartDTO) {
        Cart cart = this.modelMapper.map(cartDTO, Cart.class);
        return cart;
    }

    /**
     * MAPPING Cart TO CartDTO
     * @param cart
     * @return CartDTO
     */
    private CartDTO cartToDto (Cart cart) {
        CartDTO cartDTO = this.modelMapper.map(cart, CartDTO.class);
        return cartDTO;
    }
}
