package com.sudoo.productservice.controller

import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.common.Constants
import com.sudoo.domain.validator.ProductValidator
import com.sudoo.productservice.dto.CategoryProductDto
import com.sudoo.productservice.dto.UpsertProductDto
import com.sudoo.productservice.dto.UserProductDto
import com.sudoo.productservice.model.CategoryProduct
import com.sudoo.productservice.service.CategoryService
import com.sudoo.productservice.service.ImageService
import com.sudoo.productservice.service.ProductService
import com.sudoo.productservice.service.UserProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/discovery/products")
class ProductController(
    private val productService: ProductService,
    private val imageService: ImageService,
    private val categoryService: CategoryService,
    private val userProductService: UserProductService
) : BaseController() {

    @PostMapping
    suspend fun upsertProduct(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestBody productDto: UpsertProductDto,
    ): ResponseEntity<BaseResponse<*>> = handle {
        productService.upsertProduct(userId, productDto)
    }

    @PatchMapping
    suspend fun pathProduct(
        @RequestBody productDto: UpsertProductDto,
    ): ResponseEntity<BaseResponse<*>> = handle {
        productService.patchProduct(productDto)
    }

    @DeleteMapping("/{productId}")
    suspend fun deleteProduct(
        @PathVariable("productId") productId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        productService.deleteProduct(productId)
    }

    @GetMapping
    suspend fun getListProductInfo(
        @RequestParam("offset", required = false, defaultValue = Constants.DEFAULT_OFFSET) offset: Int,
        @RequestParam("limit", required = false, defaultValue = Constants.DEFAULT_LIMIT) limit: Int,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val offsetRequest = OffsetRequest(offset, limit)
        productService.getListProductInfo(offsetRequest)
    }

    @GetMapping("/{identify}")
    suspend fun getProductDetail(
        @PathVariable("identify") identify: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        if (ProductValidator.validateSku(identify)) {
            productService.getProductDetailBySku(identify)
        } else {
            productService.getProductDetailById(identify)
        }
    }

    @GetMapping("/sku/{sku}")
    suspend fun getProductDetailBySku(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @PathVariable("sku") sku: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        productService.getProductDetailBySku(sku)
    }

    @GetMapping("/{productId}/images")
    suspend fun getImagesByProductId(
        @PathVariable("productId") productId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        imageService.getImageByOwnerId(productId)
    }

    @GetMapping("/{productId}/categories")
    suspend fun getCategoriesByProductId(
        @PathVariable("productId") productId: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        categoryService.getCategoriesByProductId(productId)
    }

    @PostMapping("/{productId}/categories/{categoryId}")
    suspend fun upsertCategoryToProduct(
        @PathVariable("productId") productId: String,
        @PathVariable("categoryId") categoryId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        val categoryProduct = CategoryProductDto(
            categoryId = categoryId,
            productId = productId
        )
        productService.addProductToCategory(categoryProduct)
    }

    @DeleteMapping("/{productId}/categories/{categoryId}")
    suspend fun deleteProductFromCategory(
        @PathVariable("productId") productId: String,
        @PathVariable("categoryId") categoryId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        val categoryProduct = CategoryProductDto(
            categoryId = categoryId,
            productId = productId
        )
        productService.deleteProductOfCategory(categoryProduct)
    }

    @PostMapping("/comments")
    suspend fun upsertComment(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestBody comment: UserProductDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        userProductService.upsertComment(userId, comment)
    }

    @DeleteMapping("/comments/{commentId}")
    suspend fun deleteComment(
        @PathVariable("commentId") commentId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        userProductService.deleteComment(commentId)
    }

    @GetMapping("/{productId}/comments")
    suspend fun getCommentsByProductId(
        @PathVariable("productId") productId: String,
        @RequestParam("offset", required = false, defaultValue = Constants.DEFAULT_OFFSET) offset: Int,
        @RequestParam("limit", required = false, defaultValue = Constants.DEFAULT_LIMIT) limit: Int,
    ): ResponseEntity<BaseResponse<*>> = handle {
        userProductService.getCommentsByProductId(productId, OffsetRequest(offset, limit))
    }

    @GetMapping("/headers")
    suspend fun getHeader(
        @RequestHeader header: Map<String, *>
    ): ResponseEntity<BaseResponse<*>> = handle {
        header
    }
}