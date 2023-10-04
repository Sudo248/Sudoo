package com.sudoo.authservice.controller.dto

data class VerifyDto(
    val emailOrPhoneNumber: String,
    val otp: String,
)