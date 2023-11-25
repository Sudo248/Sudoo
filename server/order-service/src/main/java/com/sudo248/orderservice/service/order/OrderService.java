package com.sudo248.orderservice.service.order;

import com.sudo248.domain.exception.ApiException;
import com.sudo248.orderservice.controller.order.dto.*;
import com.sudo248.orderservice.repository.entity.order.Order;
import com.sudo248.orderservice.repository.entity.order.OrderStatus;
import com.sudo248.orderservice.repository.entity.order.OrderSupplier;
import com.sudo248.orderservice.repository.entity.order.StatisticRevenueCondition;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderService {
    List<OrderDto> getOrdersByUserId(String userId) throws ApiException;
    OrderDto getOrderById(String orderId) throws ApiException;
    OrderDto createOrder(String userId, UpsertOrderDto upsertOrderDto) throws ApiException;
    boolean deleteOrder(String orderId);

    boolean cancelOrderByUser(String orderId) throws ApiException;

    OrderDto toDto(Order order) throws ApiException;

    OrderSupplierDto toOrderSupplierDto(OrderSupplier orderSupplier) throws ApiException;

    Map<String, ?> updateOrderByField(String invoiceId, String field, String fieldId) throws ApiException;

    UpsertOrderPromotionDto updateOrderPromotion(String orderId, UpsertOrderPromotionDto upsertOrderPromotionDto) throws ApiException;

    void updateOrderPayment(String invoiceId, String paymentId);

    OrderDto getOrderByOrderSupplierIdAndSupplierFromUserId(String orderSupplierId, String userId) throws  ApiException;

    List<OrderSupplierInfoDto> getListOrderSupplierInfoFromUserId(String userId, OrderStatus status) throws  ApiException;

    List<OrderUserInfoDto> getListOrderUserInfoByUserId(String userId, List<OrderStatus> status) throws ApiException;

    Map<String, Object> patchOrderSupplier(String userId, String orderSupplierId, PatchOrderSupplierDto patchOrderSupplierDto) throws ApiException;

    RevenueStatisticData statisticRevenue(String userId, StatisticRevenueCondition condition, LocalDate from, LocalDate to);
}
