package com.sudo248.sudoo.data.dto.user

import com.sudo248.sudoo.domain.entity.auth.Role
import com.sudo248.sudoo.domain.entity.user.Gender
import java.util.Date

data class UserDto(
    val userId: String,
    val fullName: String,
    val emailOrPhoneNumber: String,
    val dob: Date,
    val bio: String = "",
    val avatar: String,
    val cover: String = "",
    val address: AddressDto,
    val gender: Gender,
    val role: Role = Role.CONSUMER
)
