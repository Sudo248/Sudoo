package com.sudoo.cartservice.controller.dto

data class AddSupplierProductDto(
        val supplierId: String,
        val productId: String,
        val amount: Int,
        val totalPrice: Double
)