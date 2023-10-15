package com.sudoo.cartservice.service

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.repository.entity.CartProduct


interface CartProductService {

    suspend fun addProductToActiveCart(
            userId: String,
            cartProduct: CartProduct
    ): CartDto

    suspend fun updateProductInCart(
            userId: String,
            cartProduct: CartProduct
    ): CartDto

    suspend fun deleteCartProduct(
            userId: String?,
            cartProduct: CartProduct
    ): CartDto
}