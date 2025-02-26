package com.sudo248.sudoo.data.mapper

import com.sudo248.sudoo.data.dto.payment.PaymentDto
import com.sudo248.sudoo.domain.entity.payment.Payment
import com.sudo248.sudoo.domain.entity.payment.PaymentStatus
import com.sudo248.sudoo.domain.entity.payment.PaymentType

fun PaymentDto.toPayment(): Payment {
    return Payment(
        paymentId,
        paymentUrl,
        PaymentType.fromString(paymentType),
        bankCode,
        orderId,
        orderType,
        PaymentStatus.valueOf(paymentStatus),
        amount
    )
}