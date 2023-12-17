package com.sudo248.invoiceservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private String cartId;
    private Double totalPrice;
    private int totalAmount;
    private String status;
    private List<CartSupplierProductDto> cartSupplierProducts;
}
