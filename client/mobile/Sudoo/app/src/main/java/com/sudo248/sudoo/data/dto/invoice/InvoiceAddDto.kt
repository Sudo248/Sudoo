package com.sudo248.sudoo.data.dto.invoice

import com.sudo248.sudoo.domain.entity.invoice.OrderStatus

data class InvoiceAddDto(
    val invoiceId: String? = null,
    val cartId: String,
    val paymentId: String? = null,
    val promotionId: String? = null,
    val status: OrderStatus? = null,
    val totalPrice: Double = 0.0,
    val totalPromotionPrice: Double = 0.0,
    val finalPrice: Double = 0.0,
)