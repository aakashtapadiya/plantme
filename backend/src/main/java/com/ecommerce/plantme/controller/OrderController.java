package com.ecommerce.plantme.controller;

import com.ecommerce.plantme.entity.Order;
import com.ecommerce.plantme.payloads.OrderDTO;
import com.ecommerce.plantme.payloads.UserDTO;
import com.ecommerce.plantme.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/processOrder/{userId}/{paymentId}")
    public ResponseEntity<OrderDTO> processOrder(@PathVariable Long userId, @PathVariable Long paymentId){
        OrderDTO orderDTO = orderService.processOrder(userId, paymentId);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }
}
