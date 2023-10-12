package com.sudo248.sudoo.domain.entity.payment

data class Payment(
    val paymentId: String = "",
    val paymentUrl: String? = null,
    val paymentType: PaymentType,
    val bankCode: String? = null,
    val orderId: String,
    val orderType: String = "100000",
    val paymentStatus: PaymentStatus,
    val amount: Double
)