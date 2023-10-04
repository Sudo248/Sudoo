package com.sudoo.cartservice.controller.dto

import com.sudoo.cartservice.repository.entity.CartSupplierProduct

data class CartDto(
        val cartId:String,
        val totalPrice: Double,
        val totalAmount: Int,
        val status:String,
        val cartSupplierProducts: List<CartSupplierProduct>
)