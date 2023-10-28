package com.sudoo.productservice.dto

import java.time.LocalDateTime

data class UpsertUserProductDto(
    val userProductId: String? = null,
    val productId: String? = null,
    val userId: String? = null,
    val rate: Float? = null,
    val comment: String? = null,
    var createdAt: LocalDateTime? = null,
    var images: List<String>? = null,
)