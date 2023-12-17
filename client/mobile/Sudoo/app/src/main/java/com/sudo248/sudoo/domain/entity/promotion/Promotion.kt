package com.sudo248.sudoo.domain.entity.promotion

import com.sudo248.base_android.base.ItemDiff

data class Promotion(
    val promotionId: String,
    val supplierId: String?,
    val value: Double,
    val name: String,
    val enable: Boolean,
    val image: String,
    val totalAmount: Int,
) : ItemDiff, java.io.Serializable {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other is Promotion && other == this
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is Promotion && other.promotionId == promotionId && other.enable == enable
    }

}
