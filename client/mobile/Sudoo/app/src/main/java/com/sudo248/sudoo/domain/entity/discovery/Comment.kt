package com.sudo248.sudoo.domain.entity.discovery

import com.sudo248.base_android.base.ItemDiff
import java.time.LocalDateTime

data class Comment(
    val commentId: String,
    val productId: String,
    val rate: Float,
    val isReviewed: Boolean,
    val comment: String,
    val updatedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val userInfo: UserInfo,
    val images: List<String>? = null,
) : ItemDiff {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other is Comment && other == this && userInfo.isContentTheSame(other.userInfo)
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is Comment && other.commentId == this.commentId &&
                other.productId == this.productId &&
                userInfo.isItemTheSame(other.userInfo)
    }

}
