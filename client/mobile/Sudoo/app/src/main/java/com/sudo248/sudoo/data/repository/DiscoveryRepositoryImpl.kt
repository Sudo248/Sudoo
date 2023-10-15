package com.sudo248.sudoo.data.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.sudoo.data.api.discovery.DiscoveryService
import com.sudo248.sudoo.data.ktx.data
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.data.mapper.toCategory
import com.sudo248.sudoo.data.mapper.toProduct
import com.sudo248.sudoo.data.mapper.toProductList
import com.sudo248.sudoo.data.mapper.toSupplier
import com.sudo248.sudoo.domain.entity.discovery.Category
import com.sudo248.sudoo.domain.entity.discovery.Offset
import com.sudo248.sudoo.domain.entity.discovery.Product
import com.sudo248.sudoo.domain.entity.discovery.ProductList
import com.sudo248.sudoo.domain.entity.discovery.Supplier
import com.sudo248.sudoo.domain.repository.DiscoveryRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiscoveryRepositoryImpl @Inject constructor(
    private val discoveryService: DiscoveryService,
    private val ioDispatcher: CoroutineDispatcher
) : DiscoveryRepository {

    override suspend fun getCategories(): DataState<List<Category>, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(discoveryService.getCategories())
            if (response.isSuccess) {
                response.data().map { it.toCategory() }
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun getCategoryById(categoryId: String): DataState<Category, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(discoveryService.getCategoryById(categoryId))
            if (response.isSuccess) {
                response.data().toCategory()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun getProductDetail(productId: String): DataState<Product, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(discoveryService.getProductDetail(productId))
            if (response.isSuccess) {
                response.data().toProduct()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun searchProductByName(productName: String): DataState<ProductList, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(discoveryService.searchProductByName(productName))
            if (response.isSuccess) {
                response.data().toProductList()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun getProductListByCategoryId(
        categoryId: String,
        offset: Offset
    ): DataState<ProductList, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(
            discoveryService.getProductListByCategoryId(
                categoryId,
                offset = offset.offset,
                limit = offset.limit
            )
        )
        if (response.isSuccess) {
            response.data().toProductList()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getSupplierDetail(
        supplierId: String
    ): DataState<Supplier, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(discoveryService.getSupplierDetail(supplierId))
        if (response.isSuccess) {
            response.data().toSupplier()
        } else {
            throw response.error().errorBody()
        }
    }

}