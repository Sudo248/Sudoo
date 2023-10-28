package com.sudoo.productservice.controller

import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.common.Constants
import com.sudoo.domain.validator.ProductValidator
import com.sudoo.productservice.dto.CategoryProductDto
import com.sudoo.productservice.dto.UpsertProductDto
import com.sudoo.productservice.dto.UserProductDto
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
        @RequestParam("categoryId", required = false, defaultValue = "") categoryId: String?,
        @RequestParam("name", required = false, defaultValue = "") name: String,
        @RequestParam("offset", required = false, defaultValue = Constants.DEFAULT_OFFSET) offset: Int,
        @RequestParam("limit", required = false, defaultValue = Constants.DEFAULT_LIMIT) limit: Int,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val offsetRequest = OffsetRequest(offset, limit)
        productService.getProductInfoByCategoryAndName(categoryId, name, offsetRequest)
    }

    @GetMapping("/recommend")
    suspend fun getRecommendListProductInfo(
        @RequestParam("offset", required = false, defaultValue = Constants.DEFAULT_OFFSET) offset: Int,
        @RequestParam("limit", required = false, defaultValue = Constants.DEFAULT_LIMIT) limit: Int,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val offsetRequest = OffsetRequest(offset, limit)
        productService.getRecommendListProductInfo(offsetRequest)
    }

    @PostMapping("/list")
    suspend fun getListProductInfoByIds(
        @RequestParam("orderInfo", required = false, defaultValue = "false") orderInfo: Boolean,
        @RequestBody ids: List<String>,
    ): ResponseEntity<BaseResponse<*>> = handle {
        if (orderInfo) {
            productService.getListOrderProductInfoByIds(ids)
        } else {
            productService.getListProductInfoByIds(ids)
        }
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

    @GetMapping("/{productId}/info")
    suspend fun getProductInfo(
        @PathVariable("productId") productId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        println("productId: $productId")
        productService.getProductInfoById(productId)
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

    @DeleteMapping("/{productId}/images/{imageId}")
    suspend fun deleteImageByProductId(
            @PathVariable("productId") productId: String,
            @PathVariable("imageId") imageId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        imageService.deleteImage(imageId)
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

    @GetMapping("/search")
    suspend fun searchProductByName(
        @RequestParam("categoryId", required = false, defaultValue = "null") categoryId: String?,
        @RequestParam("name", required = false, defaultValue = "") name: String,
        @RequestParam("offset", required = false, defaultValue = Constants.DEFAULT_OFFSET) offset: Int,
        @RequestParam("limit", required = false, defaultValue = Constants.DEFAULT_LIMIT) limit: Int,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val offsetRequest = OffsetRequest(offset, limit)
        productService.getProductInfoByCategoryAndName(categoryId, name, offsetRequest)
    }

    @GetMapping("/headers")
    suspend fun getHeader(
        @RequestHeader header: Map<String, *>
    ): ResponseEntity<BaseResponse<*>> = handle {
        header
    }
}