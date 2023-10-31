package com.sudo248.orderservice.service.order;

import com.sudo248.domain.exception.ApiException;
import com.sudo248.orderservice.controller.order.dto.OrderSupplierDto;
import com.sudo248.orderservice.controller.order.dto.UpsertOrderDto;
import com.sudo248.orderservice.controller.order.dto.OrderDto;
import com.sudo248.orderservice.controller.order.dto.UpsertOrderPromotionDto;
import com.sudo248.orderservice.repository.entity.order.Order;
import com.sudo248.orderservice.repository.entity.order.OrderSupplier;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<OrderDto> getOrdersByUserId(String userId) throws ApiException;
    OrderDto getOrderById(String orderId) throws ApiException;
    OrderDto createOrder(String userId, UpsertOrderDto upsertOrderDto) throws ApiException;
    boolean deleteOrder(String orderId);

    boolean cancelOrderByUser(String orderId);

    OrderDto toDto(Order order) throws ApiException;

    OrderSupplierDto toOrderSupplierDto(OrderSupplier orderSupplier) throws ApiException;

    Map<String, ?> updateOrderByField(String invoiceId, String field, String fieldId) throws ApiException;

    UpsertOrderPromotionDto updateOrderPromotion(String orderId, UpsertOrderPromotionDto upsertOrderPromotionDto) throws ApiException;

    void updateOrderPayment(String invoiceId, String paymentId);
}
