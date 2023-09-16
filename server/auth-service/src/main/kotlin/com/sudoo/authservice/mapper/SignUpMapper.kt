package com.sudoo.authservice.mapper

import com.sudoo.authservice.controller.dto.SignUpDto
import com.sudoo.authservice.model.Account
import java.time.LocalDateTime

fun SignUpDto.toModel(): Account {
    return Account(
        emailOrPhoneNumber = emailOrPhoneNumber,
        password = password,
        provider = provider,
        role = role,
        createAt = LocalDateTime.now(),
        isValidated = false
    )
}