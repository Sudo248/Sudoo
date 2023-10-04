package com.sudoo.authservice.controller.dto

data class SignInDto(
    val emailOrPhoneNumber: String,
    val password: String
)
