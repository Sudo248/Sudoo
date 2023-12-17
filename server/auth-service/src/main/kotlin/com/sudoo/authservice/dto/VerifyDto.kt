package com.sudoo.authservice.dto

data class VerifyDto(
    val emailOrPhoneNumber: String,
    val otp: String,
)