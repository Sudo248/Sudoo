package com.sudoo.authservice.service.impl

import com.sudoo.authservice.dto.AuthConfigDto
import com.sudoo.authservice.service.AuthConfigService
import org.springframework.stereotype.Service

@Service
class AuthConfigServiceImpl : AuthConfigService {
    override suspend fun getConfig(): AuthConfigDto {
        return AuthConfigDto(enableOtp = getEnableOtp())
    }

    private fun getEnableOtp(): Boolean {
        return try {
            System.getenv("ENABLE_OTP")?.toBoolean() ?: false
        } catch (e: Exception) {
            false
        }
    }
}