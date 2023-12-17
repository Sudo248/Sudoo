package com.sudo248.sudoo.domain.entity.discovery

import com.sudo248.base_android.base.ItemDiff
import java.io.Serializable
import java.time.LocalDateTime

data class Review(
    val reviewId: String,
    val rate: Float,
    val isReviewed: Boolean,
    val comment: String,
    val updatedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val userInfo: UserInfo,
    val productInfo: ProductInfo,
) : Serializable, ItemDiff {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other is Review && this == other
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is Review &&
                this.reviewId == other.reviewId &&
                this.rate == other.rate &&
                this.isReviewed == other.isReviewed
    }

}
