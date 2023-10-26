package com.sudo248.sudoo.domain.entity.order

data class Shipment(
    val shipmentType: ShipmentType = ShipmentType.STANDARD,
    val deliveryTime: Int = 0,
    val shipmentPrice: Double = 0.0,
)
