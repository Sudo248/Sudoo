package com.sudo248.sudoo.data.dto.order

import java.time.LocalDateTime

data class OrderUserInfoDto(
    val orderId:String,
    val finalPrice:Double,
    val createdAt: LocalDateTime,
    val orderSuppliers :List<OrderSupplierUserInfoDto>
)