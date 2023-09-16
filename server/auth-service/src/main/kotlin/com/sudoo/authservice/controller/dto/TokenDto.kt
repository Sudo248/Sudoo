package com.sudoo.authservice.controller.dto

import com.sudoo.domain.common.Constants

data class TokenDto(
    val token: String,
    val refreshToken: String,
    val type: String = Constants.TOKEN_TYPE,
)
