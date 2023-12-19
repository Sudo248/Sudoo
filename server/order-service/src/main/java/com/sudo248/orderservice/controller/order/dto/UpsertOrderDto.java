package com.sudo248.orderservice.controller.order.dto;

import com.sudo248.orderservice.repository.entity.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertOrderDto {
    private String orderId;
    private String cartId;
    private String paymentId;
    private String promotionId;
    private LocalDateTime createdAt;
    private OrderStatus status = OrderStatus.PREPARE;
}
