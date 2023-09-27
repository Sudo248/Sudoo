package com.sudoo.authservice.dto

data class AddressDto(
    private var addressId: String? = "",

    val provinceID: Int = 0,

    val districtID: Int = 0,

    val wardCode: Int = 0,

    val provinceName: String = "",

    val districtName: String = "",

    val wardName: String = "",

    val address: String = "",

    val location: LocationDto = LocationDto(),

    val fullAddress: String = "",
)