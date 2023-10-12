package com.sudo248.sudoo.data.dto.invoice

import com.sudo248.sudoo.data.dto.cart.CartDto
import com.sudo248.sudoo.data.dto.payment.PaymentDto
import com.sudo248.sudoo.data.dto.promotion.PromotionDto
import com.sudo248.sudoo.data.dto.user.UserDto
import com.sudo248.sudoo.domain.entity.cart.Cart
import com.sudo248.sudoo.domain.entity.invoice.OrderStatus
import com.sudo248.sudoo.domain.entity.invoice.Shipment
import com.sudo248.sudoo.domain.entity.payment.Payment
import com.sudo248.sudoo.domain.entity.promotion.Promotion
import com.sudo248.sudoo.domain.entity.user.User

data class InvoiceDto(
    val invoiceId: String,
    val cart: CartDto,
    val payment: PaymentDto? = null,
    val promotion: PromotionDto? = null,
    val user: UserDto,
    val status: OrderStatus,
    val shipment: Shipment,
    val totalPrice: Double,
    val totalPromotionPrice: Double,
    val finalPrice: Double,
)