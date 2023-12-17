package com.sudoo.shipment.model

data class TokenDto(
    val token: String,
    val refreshToken: String? = null,
    val userId: String? = null,
)
