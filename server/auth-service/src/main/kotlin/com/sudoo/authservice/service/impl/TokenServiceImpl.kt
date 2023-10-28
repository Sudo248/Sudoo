package com.sudoo.authservice.service.impl

import com.sudoo.authservice.dto.TokenDto
import com.sudoo.authservice.repository.AccountRepository
import com.sudoo.authservice.service.AccountService
import com.sudoo.authservice.service.TokenService
import com.sudoo.authservice.utils.TokenUtils
import com.sudoo.domain.exception.BadRequestException
import org.springframework.stereotype.Service

@Service
class TokenServiceImpl(
    private val accountRepository: AccountRepository,
    private val tokenUtils: TokenUtils
) : TokenService {
    override suspend fun refreshToken(refreshToken: String): TokenDto {
        val userId = tokenUtils.getUserIdFromRefreshToken(refreshToken)
        val account = accountRepository.findById(userId) ?: throw BadRequestException("Refresh token invalid")
        val token = tokenUtils.generateToken(userId)
        val newRefreshToken = tokenUtils.generateRefreshToken(userId)
        return TokenDto(
            token = token,
            refreshToken = newRefreshToken,
            role = account.role
        )
    }
}