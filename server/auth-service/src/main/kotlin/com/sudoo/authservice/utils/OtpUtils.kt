package com.sudoo.authservice.utils

import com.sudoo.authservice.controller.dto.VerifyDto

interface OtpUtils {
    suspend fun generateOtp(emailOrPhoneNumber: String): Boolean
    suspend fun verifyOtp(verifyDto: VerifyDto): Boolean
}