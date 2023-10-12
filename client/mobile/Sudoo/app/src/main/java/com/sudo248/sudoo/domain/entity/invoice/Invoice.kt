package com.sudo248.sudoo.domain.entity.invoice

import com.sudo248.sudoo.domain.entity.cart.Cart
import com.sudo248.sudoo.domain.entity.payment.Payment
import com.sudo248.sudoo.domain.entity.promotion.Promotion
import com.sudo248.sudoo.domain.entity.user.User

data class Invoice(
    val invoiceId: String,
    val cart: Cart,
    val payment: Payment? = null,
    val promotion: Promotion? = null,
    val user: User,
    val status: OrderStatus,
    val shipment: Shipment,
    val totalPrice: Double,
    val totalPromotionPrice: Double,
    val finalPrice: Double,
)
