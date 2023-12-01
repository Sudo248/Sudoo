package com.sudo248.sudoo.domain.entity.order

import com.sudo248.sudoo.domain.entity.payment.PaymentInfo
import com.sudo248.sudoo.domain.entity.promotion.Promotion
import com.sudo248.sudoo.domain.entity.promotion.PromotionInfo
import com.sudo248.sudoo.domain.entity.user.User
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
