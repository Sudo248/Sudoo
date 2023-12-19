package com.sudoo.authservice.mapper

import com.sudoo.authservice.dto.UserDto
import com.sudoo.authservice.model.Account

fun Account.toUserDto(): UserDto {
    return UserDto(
        userId = userId,
        fullName = emailOrPhoneNumber.substringBefore("@"),
        emailOrPhoneNumber = emailOrPhoneNumber
    )
}