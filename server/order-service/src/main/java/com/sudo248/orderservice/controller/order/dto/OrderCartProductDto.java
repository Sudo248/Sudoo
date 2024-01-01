package com.sudo248.orderservice.controller.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartProductDto {
    private String cartProductId;
    private String cartId;
    private OrderProductInfoDto product;
    private int quantity;
    private double totalPrice;
    private Double purchasePrice;
}