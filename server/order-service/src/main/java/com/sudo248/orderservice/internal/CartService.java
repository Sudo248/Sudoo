package com.sudo248.orderservice.internal;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.common.Constants;
import com.sudo248.orderservice.controller.order.dto.CartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "CART-SERVICE")
@Service
public interface CartService {

    @GetMapping("/api/v1/carts/active")
    ResponseEntity<BaseResponse<CartDto>> getActiveCartByUserId(@RequestHeader(Constants.HEADER_USER_ID) String userId);

    @GetMapping("/api/v1/carts/{cartId}")
    ResponseEntity<BaseResponse<CartDto>> getCartById(
            @PathVariable("cartId") String cartId
    );

    @PutMapping("/api/v1/carts/active/completed")
    ResponseEntity<BaseResponse<?>> updateStatusCart(
            @RequestHeader(Constants.HEADER_USER_ID) String userId
    );

    @DeleteMapping("/api/v1/carts/processing")
    ResponseEntity<BaseResponse<?>> deleteProcessingCart(
            @RequestHeader(Constants.HEADER_USER_ID) String userId
    );
}
