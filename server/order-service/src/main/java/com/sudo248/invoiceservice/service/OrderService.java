package com.sudo248.invoiceservice.service;

import com.sudo248.domain.exception.ApiException;
import com.sudo248.invoiceservice.controller.dto.UpsertOrderDto;
import com.sudo248.invoiceservice.controller.dto.OrderDto;
import com.sudo248.invoiceservice.repository.entity.Order;

import java.util.List;

public interface OrderService {
    List<OrderDto> getOrdersByUserId(String userId) throws ApiException;
    OrderDto getOrderById(String orderId) throws ApiException;
    UpsertOrderDto upsertOrder(String userId, UpsertOrderDto upsertOrderDto) throws ApiException;
    boolean deleteInvoice(String orderId);
    OrderDto toDto(Order order) throws ApiException;

    Order toEntity(OrderDto orderDto);

    OrderDto updateOrderByField(String invoiceId, String field, String fieldId) throws ApiException;

    void updateOrderPayment(String invoiceId, String paymentId);
}
