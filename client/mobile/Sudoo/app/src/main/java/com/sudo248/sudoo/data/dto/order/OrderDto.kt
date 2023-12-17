package com.sudo248.sudoo.data.dto.order

import com.sudo248.sudoo.data.dto.promotion.PromotionDto
import com.sudo248.sudoo.data.dto.user.UserDto
import com.sudo248.sudoo.domain.entity.payment.PaymentInfo
import com.sudo248.sudoo.domain.entity.promotion.PromotionInfo
import java.time.LocalDateTime

data class OrderDto(
    val orderId: String,
    val cartId: String,
    val payment: PaymentInfo? = null,
    val promotion: PromotionInfo? = null,
    val user: UserDto,
    val totalPrice: Double,
    val totalPromotionPrice: Double,
    val totalShipmentPrice: Double,
    val finalPrice: Double,
    val address: String,
    val createdAt: LocalDateTime,
    val orderSuppliers: List<OrderSupplierDto>
)