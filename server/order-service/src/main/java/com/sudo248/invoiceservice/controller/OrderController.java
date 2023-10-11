package com.sudo248.invoiceservice.controller;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.common.Constants;
import com.sudo248.domain.exception.ApiException;
import com.sudo248.invoiceservice.controller.dto.UpsertOrderDto;
import com.sudo248.invoiceservice.controller.dto.OrderDto;
import com.sudo248.invoiceservice.service.OrderService;
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
    public ResponseEntity<BaseResponse<?>> addInvoice(
            @RequestHeader(Constants.HEADER_USER_ID) String userId,
            @RequestBody UpsertOrderDto upsertOrderDto
    ) throws ApiException {
        UpsertOrderDto upsertOrderDto1 = orderService.upsertOrder(userId, upsertOrderDto);
        return BaseResponse.ok(upsertOrderDto1);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getOrderByUserId(
            @RequestHeader(Constants.HEADER_USER_ID) String userId
    ) throws ApiException {
        List<OrderDto> list = orderService.getOrdersByUserId(userId);
        return BaseResponse.ok(list);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<BaseResponse<?>> getInvoiceByInvoiceId(@PathVariable String invoiceId) throws ApiException {
        OrderDto orderDto = orderService.getOrderById(invoiceId);
        return BaseResponse.ok(orderDto);
    }

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<BaseResponse<?>> deleteInvoiceByInvoiceId(@PathVariable String invoiceId) {
        boolean res = orderService.deleteInvoice(invoiceId);
        if(res == true)
            return BaseResponse.ok(invoiceId);
        return BaseResponse.ok(null);
    }

    @PutMapping("/{orderId}/update/{field}/{fieldId}")
    public ResponseEntity<BaseResponse<?>> updateInvoiceByCase(
            @PathVariable(name = "orderId") String orderId,
            @PathVariable(name = "field") String field,
            @PathVariable(name = "fieldId") String fieldId
    ) throws ApiException {
        OrderDto orderDto = orderService.updateOrderByField(orderId, field, fieldId);
        return BaseResponse.ok(orderDto);
    }

    @PutMapping("/{orderId}/payment/{paymentId}")
    ResponseEntity<BaseResponse<?>> patchPaymentInvoice(
            @PathVariable("orderId") String orderId,
            @PathVariable("paymentId") String paymentId
    ) {
        orderService.updateOrderPayment(orderId, paymentId);
        return BaseResponse.ok(true);
    }
}
