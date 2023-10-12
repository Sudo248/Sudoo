package com.sudo248.sudoo.domain.entity.promotion

import com.sudo248.base_android.base.ItemDiff

data class Promotion(
    val promotionId: String,
    val value: Double,
    val name: String
) : ItemDiff, java.io.Serializable {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other == this
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return (other as Promotion).promotionId == promotionId
    }

}
