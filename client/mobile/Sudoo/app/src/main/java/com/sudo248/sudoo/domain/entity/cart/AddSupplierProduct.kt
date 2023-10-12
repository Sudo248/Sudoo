package com.sudo248.sudoo.domain.entity.cart

data class AddSupplierProduct(
    val supplierId: String,
    val productId: String,
    val amount: Int
)
