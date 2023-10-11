package com.sudoo.cartservice.service

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartProductDto
import org.springframework.stereotype.Service


interface CartProductService {

    suspend fun addProductToActiveCart(
            userId: String,
            cartProductDto: CartProductDto
    ): CartDto

    suspend fun updateProductInCart(
            cartId: String,
            cartProductDto: CartProductDto
    ): CartDto

    suspend fun deleteCartProduct(
            userId: String?,
            cartProductDto: CartProductDto
    ): CartDto
}