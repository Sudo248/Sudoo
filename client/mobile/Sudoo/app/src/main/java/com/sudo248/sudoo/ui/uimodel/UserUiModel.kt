package com.sudo248.sudoo.ui.uimodel

import androidx.databinding.ObservableField
import com.sudo248.sudoo.domain.entity.auth.Role
import com.sudo248.sudoo.domain.entity.user.Gender
import com.sudo248.sudoo.domain.entity.user.User

data class UserUiModel(
    var userId: String = "",
    val avatar: ObservableField<String> = ObservableField(""),
    val cover: ObservableField<String> = ObservableField(""),
    val fullName: ObservableField<String> = ObservableField(""),
    val gender: ObservableField<String> = ObservableField(Gender.OTHER.value),
    val phone: ObservableField<String> = ObservableField(""),
    val address: AddressUiModel = AddressUiModel(),
    val dob: ObservableField<String> = ObservableField(""),
    val role: ObservableField<String> = ObservableField(Role.CONSUMER.value)
)