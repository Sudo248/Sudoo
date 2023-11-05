package com.sudoo.productservice.controller

import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.common.Constants
import com.sudoo.productservice.dto.CategoryDto
import com.sudoo.productservice.dto.CategoryProductDto
import com.sudoo.productservice.service.CategoryService
import com.sudoo.productservice.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/discovery/categories")
class CategoryController(
    private val categoryService: CategoryService,
    private val productService: ProductService,
) : BaseController() {

    @GetMapping
    suspend fun getCategories(
        @RequestParam("select", required = false, defaultValue = "") select: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        categoryService.getCategories(select)
    }

    @GetMapping("/{categoryId}")
    suspend fun getCategoryById(
        @PathVariable("categoryId") categoryId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        categoryService.getCategoryById(categoryId)
    }

    @PostMapping
    suspend fun upsertCategory(
        @RequestBody categoryDto: CategoryDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        categoryService.upsertCategory(categoryDto)
    }

    @DeleteMapping("/{categoryId}")
    suspend fun deleteCategory(
        @PathVariable("categoryId") categoryId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        categoryService.deleteCategory(categoryId)
    }

    @GetMapping("/{categoryId}/products")
    suspend fun getProductByCategory(
        @PathVariable("categoryId") categoryId: String,
        @RequestParam("offset", required = false, defaultValue = Constants.DEFAULT_OFFSET) offset: Int,
        @RequestParam("limit", required = false, defaultValue = Constants.DEFAULT_LIMIT) limit: Int,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val offsetRequest = OffsetRequest(offset, limit)
        productService.getListProductInfoByCategory(categoryId, offsetRequest)
    }

    @PostMapping("/{categoryId}/products")
    suspend fun upsertProductToCategory(
        @PathVariable("categoryId") categoryId: String,
        @RequestBody categoryProduct: CategoryProductDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        categoryProduct.categoryId = categoryId
        productService.addProductToCategory(categoryProduct)
    }

    @DeleteMapping("/{categoryId}/products")
    suspend fun deleteProductFromCategory(
        @PathVariable("categoryId") categoryId: String,
        @RequestBody categoryProduct: CategoryProductDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        categoryProduct.categoryId = categoryId
        productService.deleteProductOfCategory(categoryProduct)
    }


}