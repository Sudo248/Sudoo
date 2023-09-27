package com.sudoo.productservice.controller

import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.common.Constants
import com.sudoo.productservice.dto.ProductDto
import com.sudoo.productservice.dto.UpsertProductDto
import com.sudoo.productservice.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.ws.rs.QueryParam

@RestController
@RequestMapping("/discovery/product")
class ProductController(
    private val productService: ProductService
) : BaseController() {

    @PostMapping
    suspend fun upsertProduct(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestBody productDto: UpsertProductDto,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val response = productService.upsertProduct(userId, productDto)
        BaseResponse.ok(response)
    }

    @PatchMapping
    suspend fun pathProduct(
        @RequestBody productDto: UpsertProductDto,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val response = productService.patchProduct(productDto)
        BaseResponse.ok(response)
    }

    @DeleteMapping("/{productId}")
    suspend fun deleteProduct(
        @PathVariable("productId") productId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        val response = productService.deleteProduct(productId)
        BaseResponse.ok(response)
    }

    @GetMapping
    suspend fun getListProductInfo(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestParam("offset", required = false, defaultValue = Constants.DEFAULT_OFFSET) offset: Int,
        @RequestParam("limit", required = false, defaultValue = Constants.DEFAULT_LIMIT) limit: Int,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val offsetRequest = OffsetRequest(offset, limit)
        val response = productService.getListProductInfo(userId, offsetRequest)
        BaseResponse.ok(response)
    }

    @GetMapping("/{productId}")
    suspend fun getProduct(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @PathVariable("productId") productId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        val response = productService.getProductDetailById(userId, productId)
        BaseResponse.ok(response)
    }

    @GetMapping("/sku/{sku}")
    suspend fun getProductDetailBySku(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @PathVariable("sku") sku: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        val response = productService.getProductDetailBySku(userId, sku)
        BaseResponse.ok(response)
    }
}