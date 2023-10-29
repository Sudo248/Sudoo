package com.sudoo.cartservice.service

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartProductDto
import com.sudoo.cartservice.controller.dto.ProductInfoDto
import com.sudoo.cartservice.controller.dto.UpsertCartProductDto


interface CartService {

    //---Cart-----------
    suspend fun createCartByStatus(userId: String, status: String): CartDto
    suspend fun createNewCart(userId: String): CartDto
    suspend fun getCountItemActiveCart(userId: String): Int

    //---Active Cart-----------
    suspend fun getActiveCart(userId: String): CartDto
    suspend fun getCartById(userId: String, cartId: String, hasRoute: Boolean): CartDto
    suspend fun getCartProducts(cartId: String): List<CartProductDto>

    //---CartProductInActiveCart-------------

    suspend fun updateProductInActiveCart(
        userId: String,
        upsertCartProductDto: UpsertCartProductDto
    ): CartDto

    suspend fun deleteCartProduct(
        cartId: String,
        cartProductId: String,
    ): Boolean


    //---Processing Cart-------------

    /**
     * Tạo processing cart từ active cart
     */
    suspend fun createProcessingCart(userId: String, cartProducts: List<CartProductDto>): CartDto

    /**
     * Delete processing cart if process is interrupted (back,...)
     */
    suspend fun deleteProcessingCart(userId: String): Boolean

    /**
     * Update active cart after checkout processing cart
     */
    suspend fun checkoutProcessingCart(userId: String)

}