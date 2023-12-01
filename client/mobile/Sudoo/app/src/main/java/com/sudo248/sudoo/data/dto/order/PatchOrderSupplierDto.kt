package com.sudo248.sudoo.data.dto.order

import com.sudo248.sudoo.domain.entity.order.OrderStatus
import java.time.LocalDateTime

data class PatchOrderSupplierDto(
    val status: OrderStatus,
    val receivedDateTime: LocalDateTime,
)
