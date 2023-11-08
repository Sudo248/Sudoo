package com.sudo248.orderservice.controller.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PutAmountProductDto {
    private String productId;
    private int amount;
}
