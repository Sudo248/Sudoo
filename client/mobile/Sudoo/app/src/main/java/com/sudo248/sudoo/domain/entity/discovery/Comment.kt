package com.sudo248.sudoo.domain.entity.discovery

import java.time.LocalDateTime

data class Comment(
    val commentId: String,
    val productId: String,
    val rate: Float,
    val isLike: Boolean,
    val comment: String,
    val createAt: LocalDateTime,
    val userInfo: UserInfo,
    val images: List<String>? = null,
)
