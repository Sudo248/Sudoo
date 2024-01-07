package com.sudoo.authservice.mapper

import com.sudoo.authservice.dto.SignUpDto
import com.sudoo.authservice.model.Account
import com.sudoo.domain.common.Constants
import com.sudoo.domain.utils.IdentifyCreator
import java.time.LocalDateTime
import java.time.ZoneId

fun SignUpDto.toModel(isValidated: Boolean = false): Account {
    return Account(
        userId = IdentifyCreator.create(),
        emailOrPhoneNumber = emailOrPhoneNumber,
        password = password,
        provider = provider,
        role = role,
        createAt = LocalDateTime.now(ZoneId.of(Constants.zoneId)),
        isValidated = isValidated,
    )
}