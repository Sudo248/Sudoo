package com.sudoo.shipment.model

import java.time.LocalDateTime

data class Order(
    val orderId: String,
    val cartId: String,
    val payment: PaymentInfo? = null,
    val promotion: PromotionInfo? = null,
    val user: User,
    val totalPrice: Double,
    val totalPromotionPrice: Double,
    val totalShipmentPrice: Double,
    val finalPrice: Double,
    val address: String,
    val createdAt: LocalDateTime,
    val orderSuppliers: List<OrderSupplier>
)
