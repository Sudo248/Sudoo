package com.sudoo.shipment.model

import java.time.LocalDateTime

data class OrderSupplier(
    val orderSupplierId: String,
    val supplier: SupplierInfo,
    val promotion: Promotion? = null,
    val status: OrderStatus,
    val shipment: Shipment,
    val totalPrice: Double,
    val expectedReceiveDateTime: LocalDateTime,
    val orderCartProducts: List<OrderCartProduct>
)
