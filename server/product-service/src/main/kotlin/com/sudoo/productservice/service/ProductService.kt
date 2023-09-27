package com.sudoo.productservice.service

import com.sudoo.domain.base.PaginationDto
import com.sudoo.domain.base.OffsetRequest
import com.sudoo.productservice.dto.CategoryProductDto
import com.sudoo.productservice.dto.ProductDto
import com.sudoo.productservice.dto.ProductInfoDto
import com.sudoo.productservice.dto.UpsertProductDto
import kotlinx.coroutines.flow.Flow

interface ProductService {
    suspend fun addProductToCategory(categoryProductDto: CategoryProductDto): CategoryProductDto
    suspend fun deleteProductOfCategory(categoryProductDto: CategoryProductDto): CategoryProductDto

    suspend fun upsertProduct(userId: String, productDto: UpsertProductDto): ProductDto
    suspend fun patchProduct(productDto: UpsertProductDto): ProductDto
    suspend fun deleteProduct(productId: String): String

    suspend fun getListProductInfo(userId: String, offsetRequest: OffsetRequest): PaginationDto<Flow<ProductInfoDto>>
    suspend fun getListProductInfoByCategory(userId: String, categoryId: String, offsetRequest: OffsetRequest): PaginationDto<Flow<ProductInfoDto>>
    suspend fun getListProductInfoBySupplier(userId: String, supplierId: String, offsetRequest: OffsetRequest): PaginationDto<Flow<ProductInfoDto>>
    suspend fun getProductDetailById(userId: String, productId: String): ProductDto
    suspend fun getProductDetailBySku(userId: String, sku: String): ProductDto

    suspend fun searchProductByName(userId: String, name: String, offsetRequest: OffsetRequest): PaginationDto<Flow<ProductInfoDto>>
}