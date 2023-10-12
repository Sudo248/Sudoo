package com.sudo248.sudoo.ui.mapper

import androidx.databinding.ObservableField
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.auth.Role
import com.sudo248.sudoo.domain.entity.user.Address
import com.sudo248.sudoo.domain.entity.user.Gender
import com.sudo248.sudoo.domain.entity.user.Location
import com.sudo248.sudoo.domain.entity.user.User
import com.sudo248.sudoo.ui.uimodel.AddressUiModel
import com.sudo248.sudoo.ui.uimodel.UserUiModel
import com.sudo248.sudoo.ui.util.Utils

fun Address.toAddressUi(): AddressUiModel {
    return AddressUiModel(
        provinceID,
        districtID,
        wardCode,
        provinceName = ObservableField(provinceName),
        districtName = ObservableField(districtName),
        wardName = ObservableField(wardName),
        address = ObservableField(address),
        location = location,
        fullAddress = ObservableField(fullAddress)
    )
}

fun AddressUiModel.toAddress(location: Location): Address {
    return Address(
        provinceID = provinceID,
        districtID = districtID,
        wardCode = wardCode,
        provinceName = provinceName.get().orEmpty(),
        districtName = districtName.get().orEmpty(),
        wardName = wardName.get().orEmpty(),
        address = address.get().orEmpty(),
        location = location,
        fullAddress = ""
    )
}

fun User.toUserUi(): UserUiModel {
    return UserUiModel(
        userId = userId,
        avatar = ObservableField(avatar),
        cover = ObservableField(cover),
        fullName = ObservableField(fullName),
        gender = ObservableField(gender.value),
        phone = ObservableField(phone),
        address = address.toAddressUi(),
        dob = ObservableField(Utils.formatDob(dob)),
        role = ObservableField(role.value)
    )
}

fun UserUiModel.toUser(location: Location? = null): User {
    return User(
        userId = userId,
        avatar = avatar.get() ?: Constants.Images.DEFAULT_USER_IMAGE,
        cover = cover.get().orEmpty(),
        fullName = fullName.get().orEmpty(),
        gender = Gender.fromStringValue(gender.get().orEmpty()),
        phone = phone.get().orEmpty(),
        address = address.toAddress(location ?: Location()),
        dob = Utils.parseDob(dob.get().orEmpty()),
        role = Role.fromStringValue(role.get().orEmpty())
    )
}