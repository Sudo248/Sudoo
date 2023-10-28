package com.sudoo.authservice.dto

import com.sudoo.authservice.model.Role
import com.sudoo.domain.common.Constants

data class TokenDto(
    val token: String,
    val refreshToken: String,
    val role: Role,
    val type: String = Constants.TOKEN_TYPE,
)
