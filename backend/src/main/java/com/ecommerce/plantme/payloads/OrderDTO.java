package com.ecommerce.plantme.payloads;

import com.ecommerce.plantme.entity.enums.DeliveryStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long orderId;
    private Long userId;
    private Long paymentId;
    private LocalDateTime orderDate;
    private DeliveryStatus deliveryStatus;
    private List<OrderItemDTO> orderItems;
}
