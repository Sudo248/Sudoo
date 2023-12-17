package com.sudo248.invoiceservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String paymentId = "";
    private Double amount = 0.0;
    private String paymentType = "";
}
