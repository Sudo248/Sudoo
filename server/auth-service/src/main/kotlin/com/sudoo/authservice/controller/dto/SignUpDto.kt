package com.sudoo.authservice.controller.dto

import com.sudoo.authservice.model.Provider
import com.sudoo.authservice.model.Role

data class SignUpDto (
    val emailOrPhoneNumber: String,
    val password: String,
    val provider: Provider = Provider.AUTH_SERVICE,
    val role: Role = Role.CONSUMER,
)