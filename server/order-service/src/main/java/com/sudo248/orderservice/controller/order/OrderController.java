package com.sudo248.orderservice.controller.order;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.common.Constants;
import com.sudo248.domain.exception.ApiException;
import com.sudo248.domain.util.Utils;
import com.sudo248.orderservice.controller.order.dto.UpsertOrderDto;
import com.sudo248.orderservice.controller.order.dto.OrderDto;
import com.sudo248.orderservice.controller.order.dto.UpsertOrderPromotionDto;
import com.sudo248.orderservice.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<?>> createOrder(
            @RequestHeader(Constants.HEADER_USER_ID) String userId,
            @RequestBody UpsertOrderDto upsertOrderDto
    ) {
        return Utils.handleException(() -> {
            OrderDto savedOrder = orderService.createOrder(userId, upsertOrderDto);
            return BaseResponse.ok(savedOrder);
        });
    }

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getOrderByUserId(
            @RequestHeader(Constants.HEADER_USER_ID) String userId
    ) {
        return Utils.handleException(() -> {
            List<OrderDto> list = orderService.getOrdersByUserId(userId);
            return BaseResponse.ok(list);
        });
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<BaseResponse<?>> getOrderById(@PathVariable String orderId) throws ApiException {
        return Utils.handleException(()->{
            OrderDto orderDto = orderService.getOrderById(orderId);
            return BaseResponse.ok(orderDto);
        });
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<BaseResponse<?>> deleteOrderById(@PathVariable String orderId) {
        return Utils.handleException(() -> {
            boolean res = orderService.deleteOrder(orderId);
            if(res)
                return BaseResponse.ok(orderId);
            return BaseResponse.ok(null);
        });
    }

    @PatchMapping("/{orderId}/{field}/{fieldId}")
    public ResponseEntity<BaseResponse<?>> updateOrderByCase(
            @PathVariable(name = "orderId") String orderId,
            @PathVariable(name = "field") String field,
            @PathVariable(name = "fieldId") String fieldId
    ) {
        return Utils.handleException(() -> {
            return BaseResponse.ok(orderService.updateOrderByField(orderId, field, fieldId));
        });
    }

    @PatchMapping("/{orderId}/promotion")
    public ResponseEntity<BaseResponse<?>> updateOrderPromotion(
            @PathVariable(name = "orderId") String orderId,
            @RequestBody UpsertOrderPromotionDto upsertOrderPromotionDto
            ) {
        return Utils.handleException(() -> BaseResponse.ok(orderService.updateOrderPromotion(orderId, upsertOrderPromotionDto)));
    }

    @PatchMapping("/{orderId}/payment/{paymentId}")
    ResponseEntity<BaseResponse<?>> patchPaymentOrder(
            @PathVariable("orderId") String orderId,
            @PathVariable("paymentId") String paymentId
    ) {
        return Utils.handleException(() -> {
            orderService.updateOrderPayment(orderId, paymentId);
            return BaseResponse.ok(true);
        });
    }
}
