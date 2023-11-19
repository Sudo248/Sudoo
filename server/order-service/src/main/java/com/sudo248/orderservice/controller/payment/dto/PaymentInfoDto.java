package com.sudo248.orderservice.controller.payment.dto;

import com.sudo248.orderservice.repository.entity.payment.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoDto {
    private String paymentId;
    private Double amount;
    private String paymentType;
    private LocalDateTime paymentDateTime;
    private PaymentStatus status;
}
