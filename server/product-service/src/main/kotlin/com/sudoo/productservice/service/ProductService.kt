package com.sudoo.productservice.service

import com.sudoo.domain.base.OffsetRequest
import com.sudoo.productservice.dto.*

interface ProductService {
    suspend fun addProductToCategory(categoryProductDto: CategoryProductDto): CategoryProductDto
    suspend fun deleteProductOfCategory(categoryProductDto: CategoryProductDto): CategoryProductDto

    suspend fun upsertProduct(userId: String, productDto: UpsertProductDto): UpsertProductDto
    suspend fun patchProduct(productDto: UpsertProductDto): UpsertProductDto
    suspend fun deleteProduct(productId: String): String

    suspend fun getListProductInfo(offsetRequest: OffsetRequest): ProductPagination<ProductInfoDto>
    suspend fun getListProductInfoByCategory(categoryId: String, offsetRequest: OffsetRequest): ProductPagination<ProductInfoDto>
    suspend fun getListProductInfoBySupplier(supplierId: String, offsetRequest: OffsetRequest): ProductPagination<ProductInfoDto>
    suspend fun getProductDetailById(productId: String): ProductDto
    suspend fun getProductInfoById(productId: String): ProductInfoDto
    suspend fun getProductDetailBySku(sku: String): ProductDto

    suspend fun searchProductByName(userId: String, name: String, offsetRequest: OffsetRequest): ProductPagination<ProductInfoDto>
}