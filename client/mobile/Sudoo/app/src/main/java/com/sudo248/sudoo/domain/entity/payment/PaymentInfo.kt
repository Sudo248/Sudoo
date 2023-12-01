package com.sudo248.sudoo.domain.entity.payment

import java.time.LocalDateTime

data class PaymentInfo(
    val paymentId: String,
    val amount: Double,
    val paymentType: String,
    val paymentDateTime: LocalDateTime,
    val status: PaymentStatus,
)