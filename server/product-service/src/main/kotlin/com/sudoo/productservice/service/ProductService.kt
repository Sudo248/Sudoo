package com.sudoo.productservice.service

import com.sudoo.domain.base.BasePageDto
import com.sudoo.domain.base.Pageable
import com.sudoo.productservice.controller.dto.ProductDto
import com.sudoo.productservice.controller.dto.ProductInfoDto
import com.sudoo.productservice.controller.dto.UpsertProductDto

interface ProductService {
    suspend fun upsertProduct(userId: String, product: UpsertProductDto): ProductDto
    suspend fun patchProduct(userId: String, product: UpsertProductDto): ProductDto
    suspend fun deleteProduct(userId: String, productId: String): ProductInfoDto

    suspend fun getListProductInfo(pageable: Pageable): BasePageDto<List<ProductInfoDto>>
    suspend fun getListProductInfoByCategory(categoryId: String, pageable: Pageable): BasePageDto<List<ProductInfoDto>>
    suspend fun getListProductInfoBySupplier(supplierId: String, pageable: Pageable): BasePageDto<List<ProductInfoDto>>
    suspend fun getProductDetailById(productId: String): ProductDto
    suspend fun getProductDetailBySku(sku: String): ProductDto

    suspend fun searchProductByName(name: String, pageable: Pageable): BasePageDto<List<ProductInfoDto>>
}