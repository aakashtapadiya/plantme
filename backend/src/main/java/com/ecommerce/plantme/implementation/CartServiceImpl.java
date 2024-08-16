package com.ecommerce.plantme.implementation;

import com.ecommerce.plantme.entity.*;
import com.ecommerce.plantme.entity.CartItem;
import com.ecommerce.plantme.exceptions.ResourceNotFoundException;
import com.ecommerce.plantme.payloads.CartDTO;
import com.ecommerce.plantme.repository.CartRepo;
import com.ecommerce.plantme.repository.ProductRepo;
import com.ecommerce.plantme.repository.UserRepo;
import com.ecommerce.plantme.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepo cartItemRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;


    @Autowired
    public CartServiceImpl (
            CartRepo cartItemRepo,
            ProductRepo productRepo,
            UserRepo userRepo,
            ModelMapper modelMapper
    ) {
        this.cartItemRepo = cartItemRepo;
        this.productRepo=productRepo;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CartDTO addToCart(Long productId, Long userId) {
        CartItem cartItem=cartItemRepo.findByUserIdAndProductId(userId,productId);
        Product product=productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId",productId));
        //If product already exists change quantity else add new item in cartItem.
        if (cartItem==null){
            cartItem=new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(1);
            cartItem.setPrice(1*product.getPrice());
        }
        else{
            cartItem=cartItem;
            cartItem.setQuantity(cartItem.getQuantity()+1);
            cartItem.setPrice(cartItem.getQuantity()*product.getPrice());
        }
        cartItemRepo.save(cartItem);
        return this.cartItemToDto(cartItem);
    }


    @Override
    public List<CartDTO> findCartByUserId(Long userId) {
        // Directly fetching the cartItem items for the given userId
        List<CartItem> cartItems = cartItemRepo.findByUserId(userId);
        List<CartDTO> cartItemDTOS = new ArrayList<>();

        // Mapping cartItem entities to cartItemDTOs and calculating price
        for (CartItem c : cartItems) {
            cartItemDTOS.add(this.cartItemToDto(c));
        }

        return cartItemDTOS;
    }

    @Override
    public CartDTO updateQuantity(long userId, long productId, int quantity) {
        CartItem c = cartItemRepo.findByUserIdAndProductId(userId, productId);
        if (c == null) {
            throw new ResourceNotFoundException("cartItem", "cartItemId", String.valueOf(c));
        }
        if (quantity == 0) {
            cartItemRepo.deleteById(c.getCartId());
            return null;
        } else {
            Product p=productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
            c.setQuantity(quantity);
            c.setPrice(quantity*p.getPrice());
            cartItemRepo.save(c); // Save the updated cartItem
        }

        return cartItemToDto(c);
    }

    @Override
    public String deletecartItemForUser(Long cartItemId) {
        CartItem cartItem = cartItemRepo.findById(cartItemId).orElseThrow(()-> new ResourceNotFoundException("cartItem", "cartItemId", cartItemId));
        cartItemRepo.deleteById(cartItemId);
        return "Cart item Deleted Successfully";
    }

    /**
     * MAPPING cartItemDTO to cartItem
     * @param cartItemDTO
     * @return cartItem
     */
    private CartItem dtoTocartItem (CartDTO cartItemDTO) {
        CartItem cartItem = this.modelMapper.map(cartItemDTO, CartItem.class);
        return cartItem;
    }

    /**
     * MAPPING cartItem TO cartItemDTO
     * @param cartItem
     * @return cartItemDTO
     */
    private CartDTO cartItemToDto (CartItem cartItem) {
        return this.modelMapper.map(cartItem, CartDTO.class);
    }
}
