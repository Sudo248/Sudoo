package com.sudo248.sudoo.data.mapper

import com.sudo248.sudoo.data.dto.auth.TokenDto
import com.sudo248.sudoo.domain.entity.auth.Token

fun TokenDto.toToken(): Token {
    return Token(
        token = this.token,
        refreshToken = this.refreshToken
    )
}