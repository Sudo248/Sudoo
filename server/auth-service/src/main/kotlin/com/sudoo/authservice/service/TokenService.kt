package com.sudoo.authservice.service

import com.sudoo.authservice.dto.TokenDto

interface TokenService {
    suspend fun refreshToken(refreshToken: String): TokenDto
}