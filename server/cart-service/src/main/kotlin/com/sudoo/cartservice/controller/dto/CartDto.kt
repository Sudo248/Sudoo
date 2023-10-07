package com.sudoo.cartservice.controller.dto

import com.sudoo.cartservice.repository.entity.CartSupplierProduct

data class CartDto(
        val cartId: String = "",
        val totalPrice: Double = 0.0,
        val totalAmount: Int = 0,
        val status: String = "",
        val cartSupplierProducts: List<CartSupplierProductDto> = listOf()
)