package com.sudo248.sudoo.data.dto.order

import com.sudo248.sudoo.data.dto.discovery.SupplierInfoDto
import com.sudo248.sudoo.domain.entity.order.Shipment

data class OrderSupplierDto(
    val orderSupplierId: String,
    val supplier: SupplierInfoDto,
    val promotionId: String,
    val promotionValue: Double,
    val shipment: Shipment,
    val totalPrice: Double,
    val orderCartProducts: List<OrderCartProductDto>
)
