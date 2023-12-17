package com.sudo248.invoiceservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartSupplierProductDto {
    private SupplierProductDto supplierProduct;
    private int amount;
    private Double totalPrice;
    private String cartId;
}
