package com.sudoo.productservice.dto

import java.time.LocalDateTime

data class UserProductDto(
    val userProductId: String? = null,
    val productId: String,
    val rate: Float,
    val isLike: Boolean,
    val comment: String,
    val createAt: LocalDateTime?,
    var userInfo: UserInfoDto? = null,
    var images: List<String>? = null,
)