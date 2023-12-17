package com.sudo248.sudoo.data.dto.order

import com.sudo248.sudoo.domain.entity.order.OrderStatus

data class UpsertOrderDto(
    val orderId: String? = null,
    val cartId: String,
    val paymentId: String? = null,
    val promotionId: String? = null,
    val status: OrderStatus? = null,
)