package com.sudo248.orderservice.controller.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PutAmountPromotionDto {
    private String promotionId;
    private int amount;
}
