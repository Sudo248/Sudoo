package com.sudoo.authservice.controller.dto

import com.sudoo.authservice.model.Gender
import java.time.LocalDate

data class UserDto(
    val userId: String,
    val fullName: String,
    val emailOrPhoneNumber: String,
    val dob: LocalDate = LocalDate.now(),
    val bio: String = "New user",
    val avatar: String = "user_default.png",
    val cover: String = "user_default.png",
    val address: AddressDto = AddressDto(),
    val gender: Gender = Gender.OTHER,
)