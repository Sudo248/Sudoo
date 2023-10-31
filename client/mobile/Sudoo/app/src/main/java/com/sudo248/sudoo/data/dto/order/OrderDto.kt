package com.sudo248.sudoo.data.dto.order

import com.sudo248.sudoo.data.dto.payment.PaymentDto
import com.sudo248.sudoo.data.dto.promotion.PromotionDto
import com.sudo248.sudoo.data.dto.user.UserDto
import com.sudo248.sudoo.domain.entity.order.OrderStatus

data class OrderDto(
    val orderId: String,
    val cartId: String,
    val payment: PaymentDto? = null,
    val promotion: PromotionDto? = null,
    val user: UserDto,
    val status: OrderStatus,
    val totalPrice: Double,
    val totalPromotionPrice: Double,
    val totalShipmentPrice: Double,
    val finalPrice: Double,
    val address: String,
    val orderSuppliers: List<OrderSupplierDto>
)