package com.sudo248.sudoo.domain.entity.discovery

import com.sudo248.base_android.base.ItemDiff
import java.time.LocalDateTime

data class ProductInfo(
    val productId: String,
    val name: String,
    val sku: String,
    val images: List<String>,
    val price: Double,
    val listedPrice: Double,
    val amount: Int,
    val rate: Float,
    val discount: Int,
    val startDateDiscount: LocalDateTime?,
    val endDateDiscount: LocalDateTime?,
    val saleable: Boolean,
    val brand: String,
) : ItemDiff {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return  other is ProductInfo && other == this
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is ProductInfo && other.productId == productId && other.sku == sku
    }

}
