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
@RequestMapping("/discovery/suppliers")
class SupplierController(
    private val supplierService: SupplierService,
    private val productService: ProductService,
) : BaseController() {

    @GetMapping
    suspend fun getSuppliers(): ResponseEntity<BaseResponse<*>> = handle {
        supplierService.getSuppliers()
    }

    @GetMapping("/{supplierId}")
    suspend fun getSupplierById(
        @PathVariable("supplierId") supplierId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        supplierService.getSupplierById(supplierId)
    }

    @GetMapping("/{supplierId}/info")
    suspend fun getSupplierInfoById(
        @PathVariable("supplierId") supplierId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        supplierService.getSupplierInfoById(supplierId)
    }

    @GetMapping("/self")
    suspend fun getSupplierByUserId(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        supplierService.getSupplierByUserId(userId)
    }

    @GetMapping("/self/info")
    suspend fun getSupplierInfoByUserId(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        supplierService.getSupplierInfoByUserId(userId)
    }

    @PostMapping
    suspend fun upsertSupplier(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestBody supplierDto: SupplierDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        supplierService.upsertSupplier(userId, supplierDto)
    }

    @DeleteMapping("/{supplierId}")
    suspend fun deleteSupplier(
        @PathVariable("supplierId") supplierId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        supplierService.deleteSupplier(supplierId)
    }

    @GetMapping("/{supplierId}/products")
    suspend fun getProductBySupplier(
        @PathVariable("supplierId") supplierId: String,
        @RequestParam("offset", required = false, defaultValue = Constants.DEFAULT_OFFSET) offset: Int,
        @RequestParam("limit", required = false, defaultValue = Constants.DEFAULT_LIMIT) limit: Int,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val offsetRequest = OffsetRequest(offset, limit)
        productService.getListProductInfoBySupplier(supplierId, offsetRequest)
    }
}