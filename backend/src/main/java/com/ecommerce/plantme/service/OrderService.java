package com.ecommerce.plantme.service;

import com.ecommerce.plantme.payloads.OrderDTO;

public interface OrderService {
    public OrderDTO processOrder(Long userId, Long paymentId);
}
