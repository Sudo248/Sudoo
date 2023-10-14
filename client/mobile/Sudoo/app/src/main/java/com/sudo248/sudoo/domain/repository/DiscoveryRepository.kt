package com.sudo248.sudoo.domain.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.sudoo.domain.entity.discovery.Category
import com.sudo248.sudoo.domain.entity.discovery.Offset
import com.sudo248.sudoo.domain.entity.discovery.Product
import com.sudo248.sudoo.domain.entity.discovery.ProductList

interface DiscoveryRepository {
    suspend fun getCategories(): DataState<List<Category>, Exception>

    suspend fun getCategoryById(categoryId: String): DataState<Category, Exception>

    suspend fun getProductDetail(productId: String): DataState<Product, Exception>

    suspend fun searchProductByName(productName: String): DataState<ProductList, Exception>

    suspend fun getProductListByCategoryId(categoryId: String, offset: Offset): DataState<ProductList, Exception>
}