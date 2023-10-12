package com.sudo248.sudoo.domain.entity.cart


data class Cart(
    val cartId: String,
    val totalPrice: Double,
    val totalAmount: Int,
    val status: String,
    val cartSupplierProducts: List<CartSupplierProduct>
)
