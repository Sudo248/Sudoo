package com.sudoo.authservice.controller

import com.sudoo.authservice.dto.VerifyDto
import com.sudoo.authservice.service.OtpService
import com.sudoo.domain.base.BaseController
import org.springframework.web.bind.annotation.*

@RestController
class OtpController(
    private val otpService: OtpService,
) : BaseController() {

    @GetMapping("/generate-otp/{emailOrPhoneNumber}")
    suspend fun generateOtp(@PathVariable emailOrPhoneNumber: String) = handle {
        otpService.generateOtp(emailOrPhoneNumber)
    }

    @PostMapping("/verify-otp")
    suspend fun verifyOtp(@RequestBody verifyDto: VerifyDto) = handle {
        otpService.verifyOtp(verifyDto)
    }

}