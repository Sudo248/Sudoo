package com.sudo248.orderservice.controller.order.dto;

import com.sudo248.orderservice.repository.entity.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSupplierUserInfoDto {
    private String orderSupplierId;
    private String supplierId;
    private String supplierName;
    private String supplierAvatar;
    private String supplierBrand;
    private String supplierContactUrl;
    private OrderStatus status;
    private LocalDateTime expectedReceiveDateTime;
    private double totalPrice;
    private List<OrderCartProductDto> orderCartProducts;
}
