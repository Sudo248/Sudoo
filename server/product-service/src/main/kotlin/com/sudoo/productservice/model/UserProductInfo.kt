package com.sudoo.productservice.model

data class UserProductInfo(
    val userProductId: String,
    val productId: String,
    val rate: Float,
    val isLike: Boolean,
)
