package com.ecommerce.plantme.implementation;

import com.ecommerce.plantme.entity.*;
import com.ecommerce.plantme.entity.Cart;
import com.ecommerce.plantme.exceptions.ResourceNotFoundException;
import com.ecommerce.plantme.payloads.CartDTO;
import com.ecommerce.plantme.repository.CartRepo;
import com.ecommerce.plantme.repository.ProductRepo;
import com.ecommerce.plantme.repository.UserRepo;
import com.ecommerce.plantme.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

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
        Cart cartItem=cartRepo.findByUserIdAndProductId(user,product);
        Cart cart=null;
        //If product already exists change quantity else add new item in cart.
        if (ObjectUtils.isEmpty(cartItem)){
            cart=new Cart();
            cart.setUser(user);
            cart.setProduct(product);
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
        List<Double> countAndPrice = new ArrayList<>();

        // Fetching the count of items in the cart for the given userId
        Double count = Double.valueOf(cartRepo.countByUser_UserId(userId));

        // Fetching the cart items directly using the userId
        List<Cart> carts = cartRepo.findByUser_UserId(userId);

        Double totalPrice = 0.0;

        // Calculate the total price of the items in the cart
        for (Cart c : carts) {
            Product p = productRepo.findById(c.getProduct().getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", c.getProduct().getProductId()));
            totalPrice += (p.getPrice() * c.getQuantity());
        }

        countAndPrice.add(count);
        countAndPrice.add(totalPrice);

        // Return list containing the total count and price for UI and payment purposes.
        return countAndPrice;
    }

    @Override
    public List<CartDTO> findCartByUserId(Long userId) {
        // Directly fetching the cart items for the given userId
        List<Cart> carts = cartRepo.findByUser_UserId(userId);
        List<CartDTO> cartDTOS = new ArrayList<>();

        // Mapping Cart entities to CartDTOs and calculating price
        for (Cart c : carts) {
            Product p = productRepo.findById(c.getProduct().getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", c.getProduct().getProductId()));
            c.setPrice(p.getPrice() * c.getQuantity());
            cartDTOS.add(this.cartToDto(c));
        }

        return cartDTOS;
    }

    @Override
    public CartDTO updateQuantity(long userId, long productId, int quantity) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
        Product product = productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
        Cart c = cartRepo.findByUserIdAndProductId(user, product);
        if (c == null) {
            throw new ResourceNotFoundException("Cart", "cartId", String.valueOf(c));
        }
        if (quantity == 0) {
            cartRepo.deleteById(c.getCartId());
            return null;
        } else {
            Product p=productRepo.findById(c.getProduct().getProductId()).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", c.getProduct().getProductId()));
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
