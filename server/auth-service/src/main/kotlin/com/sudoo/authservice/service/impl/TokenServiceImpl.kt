package com.sudoo.authservice.service.impl

import com.sudoo.authservice.dto.TokenDto
import com.sudoo.authservice.service.TokenService
import com.sudoo.authservice.utils.TokenUtils
import org.springframework.stereotype.Service

@Service
class TokenServiceImpl(
    private val tokenUtils: TokenUtils
) : TokenService {
    override suspend fun refreshToken(refreshToken: String): TokenDto {
        tokenUtils.validateToken(refreshToken)
        val userId = tokenUtils.getUserIdFromRefreshToken(refreshToken)
        val token = tokenUtils.generateToken(userId)
        val newRefreshToken = tokenUtils.generateRefreshToken(token)
        return TokenDto(
            token = token,
            refreshToken = newRefreshToken
        )
    }
}