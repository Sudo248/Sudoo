package com.sudo248.invoiceservice.internal;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.common.Constants;
import com.sudo248.invoiceservice.controller.dto.CartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "CART-SERVICE")
@Service
public interface CartService {

    @GetMapping("/api/v1/cart/active")
    ResponseEntity<BaseResponse<CartDto>> getActiveCartByUserId(@RequestHeader(Constants.HEADER_USER_ID) String userId);

    @GetMapping("/api/v1/cart/{cartId}")
    ResponseEntity<BaseResponse<CartDto>> getCartById(
            @RequestHeader(Constants.HEADER_USER_ID) String userId,
            @PathVariable("cartId") String cartId,
            @RequestParam("hasRoute") boolean hasRoute
    );

}
