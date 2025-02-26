package com.sudoo.shipment.model

import java.time.LocalDateTime

data class PatchOrderSupplierDto(
    val status: OrderStatus,
    val receivedDateTime: LocalDateTime? = null,
)
