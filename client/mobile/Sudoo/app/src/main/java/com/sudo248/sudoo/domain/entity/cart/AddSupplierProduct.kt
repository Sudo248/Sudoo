package com.sudo248.sudoo.domain.entity.cart

import com.sudo248.sudoo.data.dto.cart.AddSupplierProductDto

data class AddSupplierProduct(
    val supplierId: String,
    val productId: String,
    val amount: Int
)


