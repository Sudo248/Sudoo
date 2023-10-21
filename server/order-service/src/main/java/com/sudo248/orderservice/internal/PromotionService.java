package com.sudo248.orderservice.internal;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.orderservice.controller.order.dto.PromotionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "PRODUCT-SERVICE")
@Service
public interface PromotionService {

    @GetMapping("api/v1/promotions/{promotionId}")
    ResponseEntity<BaseResponse<PromotionDto>> getPromotionById(@PathVariable("promotionId") String promotionId);

}
