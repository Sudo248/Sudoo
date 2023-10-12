package com.sudo248.sudoo.data.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.sudoo.data.api.discovery.DiscoveryService
import com.sudo248.sudoo.data.ktx.data
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.data.mapper.toAddress
import com.sudo248.sudoo.data.mapper.toCategory
import com.sudo248.sudoo.data.mapper.toCategoryInfo
import com.sudo248.sudoo.data.mapper.toProduct
import com.sudo248.sudoo.domain.entity.discovery.Category
import com.sudo248.sudoo.domain.entity.discovery.CategoryInfo
import com.sudo248.sudoo.domain.entity.discovery.Product
import com.sudo248.sudoo.domain.entity.user.Address
import com.sudo248.sudoo.domain.repository.DiscoveryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiscoveryRepositoryImpl @Inject constructor(
    private val discoveryService: DiscoveryService,
    private val ioDispatcher: CoroutineDispatcher
) : DiscoveryRepository {

    override suspend fun getCategoryInfo(): DataState<List<CategoryInfo>, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(discoveryService.getAllCategoryInfo())
        if (response.isSuccess) {
            response.data().map { it.toCategoryInfo() }
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getAllCategory(): DataState<List<Category>, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(discoveryService.getAllCategory())
        if (response.isSuccess) {
            response.data().map {
                async { it.toCategory() }
            }.awaitAll()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getCategoryById(categoryId: String): DataState<Category, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(discoveryService.getCategoryById(categoryId))
        if (response.isSuccess) {
            response.data().toCategory()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getProductById(productId: String): DataState<Product, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(discoveryService.getProductById(productId))
        if (response.isSuccess) {
            response.data().toProduct()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getSupplierAddress(supplierId: String): DataState<Address, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(discoveryService.getSupplierAddress(supplierId))
        if (response.isSuccess) {
            response.data().toAddress()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun searchProductByName(productName: String): DataState<List<Product>, Exception> = stateOn(ioDispatcher){
        val response = handleResponse(discoveryService.searchProductByName(productName))
        if (response.isSuccess) {
            response.data().map { it.toProduct() }
        } else {
            throw response.error().errorBody()
        }
    }

}