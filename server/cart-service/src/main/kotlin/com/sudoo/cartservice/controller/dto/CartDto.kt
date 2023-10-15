package com.sudoo.cartservice.controller.dto

data class CartDto(
        val cartId: String = "",
        val totalPrice: Double = 0.0,
        val totalAmount: Int = 0,
        val status: String = "",
        val cartProducts: List<CartProductDto> = listOf()
)