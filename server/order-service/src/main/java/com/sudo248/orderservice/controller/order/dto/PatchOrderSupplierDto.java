package com.sudo248.orderservice.controller.order.dto;

import com.sudo248.orderservice.repository.entity.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchOrderSupplierDto {
    private OrderStatus status;
    private LocalDateTime receivedDateTime;
}
