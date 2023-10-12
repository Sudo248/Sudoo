package com.sudo248.sudoo.domain.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.sudoo.domain.entity.discovery.Category
import com.sudo248.sudoo.domain.entity.discovery.CategoryInfo
import com.sudo248.sudoo.domain.entity.discovery.Product
import com.sudo248.sudoo.domain.entity.user.Address

interface DiscoveryRepository {

    suspend fun getCategoryInfo(): DataState<List<CategoryInfo>, Exception>

    suspend fun getAllCategory(): DataState<List<Category>, Exception>

    suspend fun getCategoryById(categoryId: String): DataState<Category, Exception>

    suspend fun getProductById(productId: String): DataState<Product, Exception>

    suspend fun getSupplierAddress(supplierId: String): DataState<Address, Exception>

    suspend fun searchProductByName(productName: String): DataState<List<Product>, Exception>
}