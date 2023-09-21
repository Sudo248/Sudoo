package com.sudoo.productservice.service.impl

import com.sudoo.domain.base.BasePageDto
import com.sudoo.domain.base.Pageable
import com.sudoo.productservice.controller.dto.ProductDto
import com.sudoo.productservice.controller.dto.ProductInfoDto
import com.sudoo.productservice.controller.dto.UpsertProductDto
import com.sudoo.productservice.repository.CategoryRepository
import com.sudoo.productservice.repository.ImageRepository
import com.sudoo.productservice.repository.ProductRepository
import com.sudoo.productservice.repository.SupplierRepository
import com.sudoo.productservice.service.ProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val supplierRepository: SupplierRepository,
    private val imageRepository: ImageRepository,
    private val categoryRepository: CategoryRepository,
) : ProductService {
    override suspend fun upsertProduct(userId: String, product: UpsertProductDto): ProductDto {
        TODO("Not yet implemented")
    }

    override suspend fun patchProduct(userId: String, product: UpsertProductDto): ProductDto {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProduct(userId: String, productId: String): ProductInfoDto {
        TODO("Not yet implemented")
    }

    override suspend fun getListProductInfo(pageable: Pageable): BasePageDto<List<ProductInfoDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getListProductInfoByCategory(
        categoryId: String,
        pageable: Pageable
    ): BasePageDto<List<ProductInfoDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getListProductInfoBySupplier(
        supplierId: String,
        pageable: Pageable
    ): BasePageDto<List<ProductInfoDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductDetailById(productId: String): ProductDto {
        TODO("Not yet implemented")
    }

    override suspend fun getProductDetailBySku(sku: String): ProductDto {
        TODO("Not yet implemented")
    }

    override suspend fun searchProductByName(name: String, pageable: Pageable): BasePageDto<List<ProductInfoDto>> {
        TODO("Not yet implemented")
    }
}