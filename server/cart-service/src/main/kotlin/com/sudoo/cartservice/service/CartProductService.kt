package com.sudoo.cartservice.service

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.UpsertCartProductDto


interface CartProductService {

    suspend fun addProductToActiveCart(
        userId: String,
        upsertCartProductDto: UpsertCartProductDto
    ): CartDto

    suspend fun updateProductInCart(
        cartId: String,
        upsertCartProductDto: UpsertCartProductDto
    ): CartDto

    suspend fun deleteCartProduct(
            cartId: String,
            cartProductId: String,
    ): Boolean
}