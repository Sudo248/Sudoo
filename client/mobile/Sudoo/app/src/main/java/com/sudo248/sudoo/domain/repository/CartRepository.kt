package com.sudo248.sudoo.domain.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.sudoo.domain.entity.cart.AddSupplierProduct
import com.sudo248.sudoo.domain.entity.cart.Cart

interface CartRepository {
    suspend fun addSupplierProduct(addSupplierProduct: AddSupplierProduct): DataState<Cart, Exception>
    suspend fun updateSupplierProduct(cartId: String, addSupplierProduct: AddSupplierProduct): DataState<Cart, Exception>
    suspend fun deleteSupplierProduct(cartId: String, addSupplierProduct: AddSupplierProduct): DataState<Cart, Exception>
    suspend fun getCart(): DataState<Cart, Exception>
    suspend fun getItemInCart(): DataState<Int, Exception>


    //--------
    suspend fun addProductToActiveCart(upsertCartProduct: AddSupplierProduct): DataState<Cart,Exception>

    suspend fun getActiveCart(): DataState<Cart,Exception>
}