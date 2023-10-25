package com.sudo248.sudoo.data.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.sudoo.data.api.cart.CartService
import com.sudo248.sudoo.data.ktx.data
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.data.mapper.toAddSupplierProductDto
import com.sudo248.sudoo.data.mapper.toCart
import com.sudo248.sudoo.domain.entity.cart.AddSupplierProduct
import com.sudo248.sudoo.domain.entity.cart.Cart
import com.sudo248.sudoo.domain.repository.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val cartService: CartService,
    private val ioDispatcher: CoroutineDispatcher
) : CartRepository {
    override suspend fun addSupplierProduct(
        addSupplierProduct: AddSupplierProduct
    ): DataState<Cart, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(
            cartService.addSupplierProduct(
                addSupplierProduct.toAddSupplierProductDto()
            )
        )
        if (response.isSuccess) {
            response.data().toCart()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun updateSupplierProduct(
        cartId: String,
        addSupplierProduct: AddSupplierProduct
    ): DataState<Cart, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(
            cartService.updateSupplierProduct(
                cartId,
                listOf(addSupplierProduct.toAddSupplierProductDto())
            )
        )
        if (response.isSuccess) {
            response.data().toCart()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun deleteSupplierProduct(
        cartId: String,
        addSupplierProduct: AddSupplierProduct
    ): DataState<Cart, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(
            cartService.deleteSupplierProduct(
                cartId,
                addSupplierProduct.supplierId,
                addSupplierProduct.productId
            )
        )
        if (response.isSuccess) {
            response.data().toCart()
        } else {
            throw response.error().errorBody()
        }
    }


    override suspend fun getCart(): DataState<Cart, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(cartService.getCart())
        if (response.isSuccess) {
            response.data().toCart()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getItemInCart(): DataState<Int, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(cartService.getItemInCart())
        if (response.isSuccess) {
            response.data()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun addProductToActiveCart(upsertCartProduct: AddSupplierProduct): DataState<Cart, Exception> =
        stateOn(ioDispatcher) {
            val response =
                handleResponse(cartService.updateProductToActiveCart(upsertCartProduct.toAddSupplierProductDto()))
            if (response.isSuccess) {
                response.data().toCart()
            } else {
                throw response.error().errorBody()
            }
        }
}