package com.sudo248.sudoo.data.dto.discovery

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class CommentDto(
    @SerializedName("userProductId")
    val commentId: String,
    val productId: String,
    val rate: Float,
    val isLike: Boolean,
    val comment: String,
    val createAt: LocalDateTime,
    val userInfo: UserInfoDto,
    val images: List<String>? = null,
)
