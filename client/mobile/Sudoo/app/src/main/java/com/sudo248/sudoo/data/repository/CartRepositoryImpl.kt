package com.sudo248.sudoo.data.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.sudoo.data.api.cart.CartService
import com.sudo248.sudoo.data.ktx.data
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.data.mapper.toAddSupplierProductDto
import com.sudo248.sudoo.data.mapper.toCart
import com.sudo248.sudoo.domain.entity.cart.AddCartProduct
import com.sudo248.sudoo.domain.entity.cart.AddCartProducts
import com.sudo248.sudoo.domain.entity.cart.Cart
import com.sudo248.sudoo.domain.entity.cart.toAddCartProductsDto
import com.sudo248.sudoo.domain.repository.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val cartService: CartService,
    private val ioDispatcher: CoroutineDispatcher
) : CartRepository {

    override suspend fun getCart(): DataState<Cart, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(cartService.getActiveCart())
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

    override suspend fun addProductToActiveCart(upsertCartProduct: AddCartProduct): DataState<Cart, Exception> =
        stateOn(ioDispatcher) {
            val response =
                handleResponse(cartService.updateProductToActiveCart(upsertCartProduct.toAddSupplierProductDto()))
            if (response.isSuccess) {
                response.data().toCart()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun getActiveCart(): DataState<Cart, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(cartService.getActiveCart())
        if (response.isSuccess) {
            response.data().toCart()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun createProcessingCart(addCartProducts: AddCartProducts): DataState<Cart, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(cartService.createProcessingCartWithProduct(addCartProducts.toAddCartProductsDto()))
        if (response.isSuccess) {
            response.data().toCart()
        } else {
            throw response.error().errorBody()
        }
    }
}