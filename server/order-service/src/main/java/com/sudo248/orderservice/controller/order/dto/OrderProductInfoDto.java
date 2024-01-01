package com.sudo248.orderservice.controller.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductInfoDto {
    private String productId;
    private String supplierId;
    private String sku;
    private String name;
    private Float price;
    private String brand;
    private List<String> images;
    private float weight;
    private float height;
    private float length;
    private float width;
}
