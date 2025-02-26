package com.sudo248.sudoo.data.dto.discovery

import com.google.gson.annotations.SerializedName

data class UpsertReviewDto(
    @SerializedName("userProductId")
    val reviewId: String,
    val rate: Float,
    val comment: String,
    val images: List<String>? = null,
)
