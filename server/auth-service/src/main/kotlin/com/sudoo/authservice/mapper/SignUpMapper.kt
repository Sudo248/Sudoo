package com.sudoo.authservice.mapper

import com.sudoo.authservice.dto.SignUpDto
import com.sudoo.authservice.model.Account
import com.sudoo.domain.utils.IdentifyCreator
import java.time.LocalDateTime

fun SignUpDto.toModel(isValidated: Boolean = false): Account {
    return Account(
        userId = IdentifyCreator.create(),
        emailOrPhoneNumber = emailOrPhoneNumber,
        password = password,
        provider = provider,
        role = role,
        createAt = LocalDateTime.now(),
        isValidated = isValidated,
    )
}