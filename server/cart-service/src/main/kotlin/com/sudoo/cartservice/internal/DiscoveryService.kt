package com.sudoo.cartservice.internal

import com.sudoo.cartservice.controller.dto.SupplierProductDto
import com.sudoo.domain.common.Constants
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@Service
 interface DiscoveryService {
    @GetMapping("/api/v1/discovery/service/supplierId/{supplierId}/productId/{productId}")
    fun getSupplierProduct(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String,
            @PathVariable("supplierId") supplierId: String,
            @PathVariable("productId") productId: String,
            @RequestParam("hasRoute") hasRoute: Boolean
    ): SupplierProductDto

    @GetMapping("/api/v1/discovery/service/supplierId/{supplierId}/productId/{productId}/price")
    fun getSupplierProductPrice(
            @PathVariable("supplierId") supplierId: String,
            @PathVariable("productId") productId: String
    ): Double
}