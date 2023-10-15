package com.sudoo.cartservice.service

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartProductDto


interface CartService {
    suspend fun createNewCart(userId: String): CartDto
    suspend fun updateStatusCart(userId: String): CartDto
    suspend fun getCartById(userId: String, cartId: String, hasRoute: Boolean): CartDto
    suspend fun getActiveCartByUserId(userId: String): CartDto
    suspend fun getCountItemActiveCart(userId: String): Int
    suspend fun getCartProducts(cartId: String): List<CartProductDto>
}