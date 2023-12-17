package com.sudoo.notification.service;

import com.sudo248.domain.base.BaseResponse;
import com.sudoo.notification.controller.dto.SupplierInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "PRODUCT-SERVICE")
@Service
public interface DiscoveryService {
    @GetMapping("/api/v1/discovery/supplier/info/{supplierId}")
    ResponseEntity<BaseResponse<SupplierInfoDto>> getSupplierInfoById(@PathVariable("supplierId") String supplierId);
}
