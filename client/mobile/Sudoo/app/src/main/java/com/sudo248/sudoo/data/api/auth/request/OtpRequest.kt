package com.sudo248.sudoo.data.api.auth.request

import com.google.gson.annotations.SerializedName


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:39 - 04/03/2023
 */
data class OtpRequest(
    @SerializedName("emailOrPhoneNumber")
    val phoneNumber: String,
    val otp: String,
)
