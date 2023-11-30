package com.sudo248.orderservice.controller.order;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.common.Constants;
import com.sudo248.domain.util.Utils;
import com.sudo248.orderservice.controller.order.dto.PatchOrderSupplierDto;
import com.sudo248.orderservice.repository.entity.order.OrderStatus;
import com.sudo248.orderservice.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/order-supplier")
public class OrderSupplierController {
    private final OrderService orderService;

    public OrderSupplierController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    ResponseEntity<BaseResponse<?>> getListOrderOfSupplier(
            @RequestHeader(Constants.HEADER_USER_ID) String userId,
            @RequestParam(value = "status", required = false) String status
    ) {
        return Utils.handleException(() -> BaseResponse.ok(orderService.getListOrderSupplierInfoFromUserId(userId, OrderStatus.fromValue(status))));
    }

    @GetMapping("/{orderSupplierId}")
    ResponseEntity<BaseResponse<?>> getOrderOfSupplierDetail(
            @PathVariable("orderSupplierId") String orderSupplierId
    ) {
        return Utils.handleException(() -> BaseResponse.ok(orderService.getOrderSupplierById(orderSupplierId)));
    }

    @GetMapping("/users")
    ResponseEntity<BaseResponse<?>> getOrderOfUser(
            @RequestHeader(Constants.HEADER_USER_ID) String userId,
            @RequestParam(value = "status", required = false) String status
    ) {
        return Utils.handleException(() -> BaseResponse.ok(orderService.getListOrderSupplierUserInfoByUserId(userId, OrderStatus.fromValues(status))));
    }

    @PatchMapping("/{orderSupplierId}")
    ResponseEntity<BaseResponse<?>> patchOrderSupplierStatus(
            @RequestHeader(Constants.HEADER_USER_ID) String userId,
            @PathVariable("orderSupplierId") String orderSupplierId,
            @RequestBody PatchOrderSupplierDto patchOrderSupplierDto
    ) {
        return Utils.handleException(() -> BaseResponse.ok(orderService.patchOrderSupplier(userId, orderSupplierId, patchOrderSupplierDto)));
    }
}
