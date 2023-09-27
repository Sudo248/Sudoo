package com.sudoo.productservice.controller

import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.common.Constants
import com.sudoo.productservice.dto.CategoryDto
import com.sudoo.productservice.service.CategoryService
import com.sudoo.productservice.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/discovery/category")
class CategoryController(
    private val categoryService: CategoryService,
    private val productService: ProductService,
) : BaseController() {

    @GetMapping
    suspend fun getCategories(): ResponseEntity<BaseResponse<*>> = handle {
        val response = categoryService.getCategories()
        BaseResponse.ok(response)
    }

    @GetMapping("/{categoryId}")
    suspend fun getCategoryById(
        @PathVariable("categoryId") categoryId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        val response = categoryService.getCategoryById(categoryId)
        BaseResponse.ok(response)
    }

    @PostMapping
    suspend fun upsertCategory(
        @RequestBody categoryDto: CategoryDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        val response = categoryService.upsertCategory(categoryDto)
        BaseResponse.ok(response)
    }

    @DeleteMapping("/{categoryId}")
    suspend fun deleteCategory(
        @PathVariable("categoryId") categoryId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        val response = categoryService.deleteCategory(categoryId)
        BaseResponse.ok(response)
    }

    @GetMapping("/{categoryId}/products")
    suspend fun getProductByCategory(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @PathVariable("categoryId") categoryId: String,
        @RequestParam("offset", required = false, defaultValue = Constants.DEFAULT_OFFSET) offset: Int,
        @RequestParam("limit", required = false, defaultValue = Constants.DEFAULT_LIMIT) limit: Int,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val offsetRequest = OffsetRequest(offset, limit)
        val response = productService.getListProductInfoByCategory(userId, categoryId, offsetRequest)
        BaseResponse.ok(response)
    }
}