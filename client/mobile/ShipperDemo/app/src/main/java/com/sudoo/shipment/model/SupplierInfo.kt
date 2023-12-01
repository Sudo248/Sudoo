package com.sudoo.shipment.model

data class SupplierInfo(
    val supplierId: String,
    val name: String,
    val avatar: String,
    val contactUrl: String,
    val rate: Float,
    val address: Address
)
