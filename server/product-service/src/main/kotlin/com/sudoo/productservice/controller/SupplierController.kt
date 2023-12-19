package com.sudoo.productservice.controller

import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.base.SortRequest
import com.sudoo.domain.common.Constants
import com.sudoo.productservice.dto.TransactionDto
import com.sudoo.productservice.dto.UpsertSupplierDto
import com.sudoo.productservice.service.ProductService
import com.sudoo.productservice.service.SupplierService
import com.sudoo.productservice.service.TransactionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/discovery/suppliers")
class SupplierController(
    private val supplierService: SupplierService,
    private val productService: ProductService,
    private val transactionService: TransactionService
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
        @RequestBody supplierDto: UpsertSupplierDto
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
        @RequestParam("sortBy", required = false, defaultValue = "") sortBy: String,
        @RequestParam("orderBy", required = false, defaultValue = "asc") orderBy: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val offsetRequest = OffsetRequest(offset, limit)
        val sortRequest = if (sortBy.isBlank()) null else SortRequest(sortBy, orderBy)
        productService.getListProductInfoBySupplier(supplierId, offsetRequest, sortRequest)
    }

    @GetMapping("/self/products")
    suspend fun getProductBySupplierSelf(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestParam("offset", required = false, defaultValue = Constants.DEFAULT_OFFSET) offset: Int,
        @RequestParam("limit", required = false, defaultValue = Constants.DEFAULT_LIMIT) limit: Int,
        @RequestParam("sortBy", required = false, defaultValue = "") sortBy: String,
        @RequestParam("orderBy", required = false, defaultValue = "asc") orderBy: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val offsetRequest = OffsetRequest(offset, limit)
        val sortRequest = if (sortBy.isBlank()) null else SortRequest(sortBy, orderBy)
        productService.getListProductInfoByUserId(userId, offsetRequest, sortRequest)
    }

    @GetMapping("/self/revenue")
    suspend fun getRevenue(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        supplierService.getRevenue(userId)
    }

    @PutMapping("/internal/transactions")
    suspend fun createAddRevenueTransaction(
        @RequestBody transactionDto: TransactionDto,
    ): ResponseEntity<BaseResponse<*>> = handle {
        supplierService.createAddRevenueTransaction(transactionDto)
    }

    @PutMapping("/self/transactions")
    suspend fun createClaimRevenueTransaction(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestBody transactionDto: TransactionDto,
    ): ResponseEntity<BaseResponse<*>> = handle {
        supplierService.createClaimRevenueTransaction(userId, transactionDto)
    }

    @GetMapping("/self/transactions")
    suspend fun getAllTransactionBySelf(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
    ) = handle {
        transactionService.getAllTransactionBySelf(userId)
    }

    @GetMapping("/{supplierId}/transactions")
    suspend fun getAllTransaction(
        @PathVariable("supplierId") supplierId: String,
    ) = handle {
        transactionService.getAllTransactionByOwnerId(supplierId)
    }
}