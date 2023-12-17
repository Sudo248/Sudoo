package com.sudoo.shipment.model

import java.time.LocalDate

data class User(
    val userId: String = "",
    val avatar: String = "",
    val cover: String = "",
    val fullName: String = "",
    val gender: Gender = Gender.OTHER,
    val phone: String = "",
    val address: Address = Address(),
    val dob: LocalDate = LocalDate.now()
)
