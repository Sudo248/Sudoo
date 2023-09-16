package com.sudoo.authservice.service

import com.sudoo.authservice.controller.dto.TokenDto
import com.sudoo.authservice.controller.dto.VerifyDto

interface OtpService {
    suspend fun generateOtp(emailOrPhoneNumber: String): Boolean
    suspend fun verifyOtp(verifyDto: VerifyDto): TokenDto?
}