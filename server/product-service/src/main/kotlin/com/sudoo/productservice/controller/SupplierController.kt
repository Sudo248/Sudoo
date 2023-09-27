package com.sudoo.productservice.controller

import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.common.Constants
import com.sudoo.productservice.dto.SupplierDto
import com.sudoo.productservice.service.ProductService
import com.sudoo.productservice.service.SupplierService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/discovery/supplier")
class SupplierController(
    private val supplierService: SupplierService,
    private val productService: ProductService,
) : BaseController() {

    @GetMapping
    suspend fun getSuppliers(): ResponseEntity<BaseResponse<*>> = handle {
        val response = supplierService.getSuppliers()
        BaseResponse.ok(response)
    }

    @GetMapping("/{supplierId}")
    suspend fun getSupplierById(
        @PathVariable("supplierId") supplierId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        val response = supplierService.getSupplierById(supplierId)
        BaseResponse.ok(response)
    }

    @GetMapping("/info/{supplierId}")
    suspend fun getSupplierInfoById(
        @PathVariable("supplierId") supplierId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        val response = supplierService.getSupplierInfoById(supplierId)
        BaseResponse.ok(response)
    }

    @GetMapping("/self")
    suspend fun getSupplierByUserId(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val response = supplierService.getSupplierByUserId(userId)
        BaseResponse.ok(response)
    }

    @PostMapping
    suspend fun upsertSupplier(
        @RequestBody supplierDto: SupplierDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        val response = supplierService.upsertSupplier(supplierDto)
        BaseResponse.ok(response)
    }

    @DeleteMapping("/{supplierId}")
    suspend fun deleteSupplier(
        @PathVariable("supplierId") supplierId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        val response = supplierService.deleteSupplier(supplierId)
        BaseResponse.ok(response)
    }

    @GetMapping("/{supplierId}/products")
    suspend fun getProductBySupplier(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @PathVariable("supplierId") supplierId: String,
        @RequestParam("offset", required = false, defaultValue = Constants.DEFAULT_OFFSET) offset: Int,
        @RequestParam("limit", required = false, defaultValue = Constants.DEFAULT_LIMIT) limit: Int,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val offsetRequest = OffsetRequest(offset, limit)
        val response = productService.getListProductInfoBySupplier(userId, supplierId, offsetRequest)
        BaseResponse.ok(response)
    }
}