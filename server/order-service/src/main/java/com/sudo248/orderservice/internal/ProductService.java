package com.sudo248.orderservice.internal;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.orderservice.controller.order.dto.SupplierInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "PRODUCT-SERVICE")
@Service
public interface ProductService {
    @GetMapping("/api/v1/discovery/suppliers/{supplierId}/info")
    BaseResponse<SupplierInfoDto> getSupplierById(@PathVariable("supplierId") String supplierId);
}
