package com.sudo248.sudoo.data.dto.payment

data class PaymentDto(
    val paymentId: String = "",
    val paymentUrl: String? = null,
    val paymentType: String,
    val bankCode: String? = null,
    val amount: Double = 0.0,
    val orderId: String,
    val orderType: String = "100000",
    val ipAddress: String? = null,
    val paymentStatus: String
)
