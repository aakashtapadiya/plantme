package com.ecommerce.plantme.implementation;


import com.ecommerce.plantme.entity.*;
import com.ecommerce.plantme.entity.enums.DeliveryStatus;
import com.ecommerce.plantme.exceptions.ResourceNotFoundException;
import com.ecommerce.plantme.payloads.OrderDTO;
import com.ecommerce.plantme.payloads.OrderItemDTO;
import com.ecommerce.plantme.payloads.PaymentDTO;
import com.ecommerce.plantme.payloads.ProductDTO;
import com.ecommerce.plantme.repository.CartRepo;
import com.ecommerce.plantme.repository.OrderRepo;
import com.ecommerce.plantme.repository.PaymentRepo;
import com.ecommerce.plantme.repository.ProductRepo;
import com.ecommerce.plantme.service.OrderService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    private CartRepo cartItemRepo;
    private OrderRepo orderRepo;
    private ProductRepo productRepo;
    private PaymentRepo paymentRepo;
    private ModelMapper modelMapper;

    public OrderServiceImpl(CartRepo carItemRepo, OrderRepo orderRepo, ProductRepo productRepo, PaymentRepo paymentRepo, ModelMapper modelMapper){
        this.cartItemRepo=carItemRepo;
        this.orderRepo=orderRepo;
        this.productRepo=productRepo;
        this.paymentRepo=paymentRepo;
        this.modelMapper=modelMapper;
    }

    @Transactional
    public OrderDTO processOrder(Long userId, Long paymentId) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "paymentId", paymentId));
        System.out.println(payment);
        List<CartItem> cartItems = cartItemRepo.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("No items in cart for the user");
        }
        System.out.println(cartItems);
        Order order = new Order();
        order.setUser(payment.getUser());
        order.setPayment(payment);
        order.setDeliveryStatus(DeliveryStatus.PENDING);
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> new OrderItem(
                        null,
                        productRepo.findById(cartItem.getProductId()).orElseThrow(()->new ResourceNotFoundException("Product","productID",cartItem.getProductId())),
                        cartItem.getQuantity(),
                        cartItem.getPrice()))
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        orderRepo.save(order);

        cartItemRepo.deleteAll(cartItems);
        return OrderToDto(order);
    }


    public OrderDTO OrderToDTO(Order order) {
        OrderDTO orderDTO=this.modelMapper.map(order, OrderDTO.class);
        return orderDTO;
    }

    /**
     * MAPPING OrderDTO to Order
     * @param orderDTO
     * @return order
     */
    private Order dtoToOrder (OrderDTO orderDTO) {
        Order order = this.modelMapper.map(orderDTO, Order.class);
        return order;
    }

    /**
     * MAPPING Order TO OrderDTO
     * @param order
     * @return OrderDTO
     */
    private OrderDTO OrderToDto (Order order) {
        OrderDTO orderDTO = this.modelMapper.map(order, OrderDTO.class);
        return orderDTO;
    }
}
