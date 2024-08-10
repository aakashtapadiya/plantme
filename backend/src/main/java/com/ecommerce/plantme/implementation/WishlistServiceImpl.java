package com.ecommerce.plantme.implementation;

import com.ecommerce.plantme.entity.Product;
import com.ecommerce.plantme.entity.User;
import com.ecommerce.plantme.entity.Wishlist;
import com.ecommerce.plantme.exceptions.CommonApiException;
import com.ecommerce.plantme.exceptions.ResourceNotFoundException;
import com.ecommerce.plantme.payloads.ProductDTO;
import com.ecommerce.plantme.repository.ProductRepo;
import com.ecommerce.plantme.repository.UserRepo;
import com.ecommerce.plantme.repository.WishlistRepo;
import com.ecommerce.plantme.service.WishlistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    private UserRepo userRepo;

    private WishlistRepo wishlistRepo;

    private ProductRepo productRepo;

    private ModelMapper modelMapper;

    @Autowired
    public WishlistServiceImpl (
            UserRepo userRepo,
            WishlistRepo wishlistRepo,
            ProductRepo productRepo,
            ModelMapper modelMapper
    ) {
        this.userRepo = userRepo;
        this.wishlistRepo = wishlistRepo;
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addProductToWishlist(Long userId, Long productId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
        Product product = productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));
        if (wishlistRepo.findWishlistItemByUserIdProductId(userId, productId) != null){
            throw new CommonApiException("Product Already Exists in Wishlist");
        }
        Wishlist wishlist;
        if (user.getWishlist() == null) {
            wishlist = new Wishlist();
            wishlist.setUser(user);
            wishlist.setProduct(new ArrayList<>());
            user.setWishlist(wishlist);
        } else {
            wishlist = user.getWishlist();
        }
        wishlist.getProduct().add(product);
        userRepo.save(user);
        return "Product added in wishlist";
    }

    @Override
    public List<ProductDTO> getAllProductsFromWishlist(Long userId) {

        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));

        if (user.getWishlist() == null){
            throw new CommonApiException("No Products available in Wishlist");
        }
        List<Product> productList = user.getWishlist().getProduct();
        if (productList.isEmpty()){
            throw new CommonApiException("No Products available in Wishlist");
        }
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productList) {
            productDTOList.add(modelMapper.map(product, ProductDTO.class));
        }
        return productDTOList;
    }

    @Override
    public String removeProductFromWishlist(Long userId, Long productId) {

        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
        Product product = productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));
        if (wishlistRepo.findWishlistItemByUserIdProductId(userId, productId) == null){
            throw new CommonApiException("Product is Not in the WishList");
        }

        Wishlist wishlist = user.getWishlist();
        wishlist.getProduct().remove(product);
        userRepo.save(user);

        return "Product removed from Wishlist";
    }
}