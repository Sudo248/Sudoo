package com.sudoo.shipment.api.request

import com.google.gson.annotations.SerializedName

data class AccountRequest(
    @SerializedName("emailOrPhoneNumber")
    val phoneNumber: String,
    val password: String
)
