package com.sudo248.sudoo.data.mapper

import com.sudo248.sudoo.data.dto.user.AddressDto
import com.sudo248.sudoo.data.dto.user.AddressSuggestionDto
import com.sudo248.sudoo.data.dto.user.UserDto
import com.sudo248.sudoo.domain.entity.user.Address
import com.sudo248.sudoo.domain.entity.user.AddressSuggestion
import com.sudo248.sudoo.domain.entity.user.Location
import com.sudo248.sudoo.domain.entity.user.User

fun AddressSuggestionDto.toAddressSuggestion(): AddressSuggestion {
    return AddressSuggestion(
        addressId,
        addressName,
        addressCode
    )
}

fun AddressDto.toAddress(): Address {
    return Address(
        addressId,
        provinceID,
        districtID,
        wardCode,
        provinceName,
        districtName,
        wardName,
        address,
        location,
        fullAddress
    )
}

fun Address.toAddressDto(location: Location? = null): AddressDto {
    return AddressDto(
        addressId,
        provinceID,
        districtID,
        wardCode,
        provinceName,
        districtName,
        wardName,
        address,
        location ?: this.location,
        fullAddress
    )
}

fun UserDto.toUser(): User {
    return User(
        userId = userId,
        avatar = avatar,
        cover = cover,
        fullName = fullName,
        gender = gender,
        phone = emailOrPhoneNumber,
        address = address.toAddress(),
        dob = dob
    )
}

fun User.toUserDto(location: Location? = null): UserDto {
    return UserDto(
        userId = userId,
        avatar = avatar,
        cover = cover,
        fullName = fullName,
        gender = gender,
        emailOrPhoneNumber = phone,
        address = address.toAddressDto(location),
        dob = dob,
        bio = ""
    )
}

