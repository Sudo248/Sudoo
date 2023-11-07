package com.sudo248.sudoo.domain.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.sudoo.domain.entity.cart.AddCartProduct
import com.sudo248.sudoo.domain.entity.cart.AddCartProducts
import com.sudo248.sudoo.domain.entity.cart.Cart

interface CartRepository {
    suspend fun getCart(): DataState<Cart, Exception>
    suspend fun countItemInActiveCart(): DataState<Int, Exception>


    //--------
    suspend fun addProductToActiveCart(upsertCartProduct: AddCartProduct): DataState<Cart,Exception>
    suspend fun getActiveCart(): DataState<Cart,Exception>

    suspend fun createProcessingCart(addCartProducts: AddCartProducts): DataState<Cart,Exception>
}