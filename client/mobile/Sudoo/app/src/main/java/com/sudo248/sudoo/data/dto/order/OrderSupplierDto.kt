package com.sudo248.sudoo.data.dto.order

import com.sudo248.sudoo.data.dto.discovery.SupplierInfoDto
import com.sudo248.sudoo.data.dto.promotion.PromotionDto
import com.sudo248.sudoo.domain.entity.order.Shipment
import java.time.LocalDateTime

data class OrderSupplierDto(
    val orderSupplierId: String,
    val supplier: SupplierInfoDto,
    val promotion: PromotionDto? = null,
    val shipment: Shipment,
    val totalPrice: Double,
    val expectedReceiveDateTime: LocalDateTime,
    val orderCartProducts: List<OrderCartProductDto>
)
