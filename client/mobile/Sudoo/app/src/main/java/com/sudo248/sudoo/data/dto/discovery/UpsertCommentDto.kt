package com.sudo248.sudoo.data.dto.discovery

data class UpsertCommentDto(
    val productId: String,
    val rate: Float,
    val isLike: Boolean,
    val comment: String,
    val images: List<String>? = null,
)
