package com.sudo248.orderservice.controller.order;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.common.Constants;
import com.sudo248.domain.exception.ApiException;
import com.sudo248.orderservice.controller.order.dto.UpsertOrderDto;
import com.sudo248.orderservice.controller.order.dto.OrderDto;
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
    ) throws ApiException {
        OrderDto savedOrder = orderService.createOrder(userId, upsertOrderDto);
        return BaseResponse.ok(savedOrder);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getOrderByUserId(
            @RequestHeader(Constants.HEADER_USER_ID) String userId
    ) throws ApiException {
        List<OrderDto> list = orderService.getOrdersByUserId(userId);
        return BaseResponse.ok(list);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<BaseResponse<?>> getOrderById(@PathVariable String orderId) throws ApiException {
        OrderDto orderDto = orderService.getOrderById(orderId);
        return BaseResponse.ok(orderDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<BaseResponse<?>> deleteOrderById(@PathVariable String orderId) {
        boolean res = orderService.deleteOrder(orderId);
        if(res)
            return BaseResponse.ok(orderId);
        return BaseResponse.ok(null);
    }

    @PatchMapping("/{orderId}/update/{field}/{fieldId}")
    public ResponseEntity<BaseResponse<?>> updateInvoiceByCase(
            @PathVariable(name = "orderId") String orderId,
            @PathVariable(name = "field") String field,
            @PathVariable(name = "fieldId") String fieldId
    ) throws ApiException {
        return BaseResponse.ok(orderService.updateOrderByField(orderId, field, fieldId));
    }

    @PatchMapping("/{orderId}/payment/{paymentId}")
    ResponseEntity<BaseResponse<?>> patchPaymentOrder(
            @PathVariable("orderId") String orderId,
            @PathVariable("paymentId") String paymentId
    ) {
        orderService.updateOrderPayment(orderId, paymentId);
        return BaseResponse.ok(true);
    }
}
