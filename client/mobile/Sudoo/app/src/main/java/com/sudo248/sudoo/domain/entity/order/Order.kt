package com.sudo248.sudoo.domain.entity.order

import com.sudo248.sudoo.domain.entity.payment.Payment
import com.sudo248.sudoo.domain.entity.promotion.Promotion
import com.sudo248.sudoo.domain.entity.user.User

data class Order(
    val orderId: String,
    val cartId: String,
    val payment: Payment? = null,
    val promotion: Promotion? = null,
    val user: User,
    val status: OrderStatus,
    val totalPrice: Double,
    val totalPromotionPrice: Double,
    val totalShipmentPrice: Double,
    val finalPrice: Double,
    val address: String,
    val orderSuppliers: List<OrderSupplier>
)
