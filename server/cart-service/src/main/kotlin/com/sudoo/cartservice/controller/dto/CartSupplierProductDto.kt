package com.sudoo.cartservice.controller.dto

data class CartSupplierProductDto(
        private var supplierProduct: SupplierProductDto? = null,
        val amount: Int = 0,
        val totalPrice: Double? = null,
        val cartId: String? = null
)