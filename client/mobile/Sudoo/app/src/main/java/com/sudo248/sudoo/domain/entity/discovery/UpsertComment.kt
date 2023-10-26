package com.sudo248.sudoo.domain.entity.discovery

data class UpsertComment(
    val productId: String,
    val rate: Float,
    val isLike: Boolean,
    val comment: String,
    var images: List<String>? = null,
)