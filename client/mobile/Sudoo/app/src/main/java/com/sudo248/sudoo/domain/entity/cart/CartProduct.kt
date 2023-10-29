package com.sudo248.sudoo.domain.entity.cart

import com.sudo248.base_android.base.ItemDiff
import com.sudo248.sudoo.data.dto.cart.ProductInfoDto

data class CartProduct(
    val cartProductId: String = "",
    val cartId: String = "",
    var quantity: Int = 0,
    val totalPrice: Double = 0.0,
    val product: ProductInfoDto? = null
) : ItemDiff {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other is CartProduct && this == other
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is CartProduct && other.product?.productId == product?.productId &&
                other.quantity == quantity
    }

}