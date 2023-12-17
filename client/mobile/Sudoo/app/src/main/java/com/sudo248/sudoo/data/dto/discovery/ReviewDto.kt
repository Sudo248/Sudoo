package com.sudo248.sudoo.data.dto.discovery

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ReviewDto(
    @SerializedName("userProductId")
    val reviewId: String,
    val rate: Float,
    val isReviewed: Boolean,
    val comment: String,
    val updatedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val userInfo: UserInfoDto,
    val productInfo: ProductInfoDto,
)