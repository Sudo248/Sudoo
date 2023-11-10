package com.sudo248.sudoo.data.api.payment.request

import java.util.TimeZone

data class PaymentRequest(
    val paymentType: String,
    val bankCode: String? = null,
    val orderId: String,
    val orderType: String = "100000",
    val ipAddress: String? = null,
    val timeZoneId: String? = null,
    val amount: Double = 0.0
)