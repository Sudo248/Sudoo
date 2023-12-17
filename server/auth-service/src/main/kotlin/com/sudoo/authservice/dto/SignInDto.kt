package com.sudoo.authservice.dto

data class SignInDto(
    val emailOrPhoneNumber: String,
    val password: String
)
