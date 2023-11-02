package com.sudo248.orderservice.internal;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.common.Constants;
import com.sudo248.orderservice.controller.order.dto.*;
import feign.Body;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "PRODUCT-SERVICE")
@Service
public interface ProductService {
    @GetMapping("/api/v1/discovery/suppliers/{supplierId}/info")
    BaseResponse<SupplierInfoDto> getSupplierById(@PathVariable("supplierId") String supplierId);

    @GetMapping("api/v1/promotions/{promotionId}")
    ResponseEntity<BaseResponse<PromotionDto>> getPromotionById(@PathVariable("promotionId") String promotionId);

    @PatchMapping("/api/v1/discovery/products/internal/amount")
    ResponseEntity<BaseResponse<?>> patchProductAmount(@RequestBody PatchAmountProductDto patchProduct);

    @PatchMapping("/api/v1/discovery/promotions/internal/amount")
    ResponseEntity<BaseResponse<?>> patchPromotionAmount(@RequestBody PatchAmountPromotionDto patchPromotion);

    @PostMapping("/api/v1/discovery/internal/user-product")
    ResponseEntity<BaseResponse<?>> upsertUserProduct(
            @RequestHeader(Constants.HEADER_USER_ID) String userId,
            @RequestBody UpsertUserProductDto upsertUserProductDto
    );
}
