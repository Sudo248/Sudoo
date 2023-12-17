package com.sudo248.invoiceservice.controller.dto;

import com.sudo248.invoiceservice.repository.entity.OrderStatus;
import com.sudo248.invoiceservice.repository.entity.Shipment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String orderId;
    private CartDto cart;
    private PaymentDto payment;
    private PromotionDto promotion;
    private UserDto user;
    private OrderStatus status;
    private Shipment shipment;
    private Double totalPrice, totalPromotionPrice, finalPrice;
}
