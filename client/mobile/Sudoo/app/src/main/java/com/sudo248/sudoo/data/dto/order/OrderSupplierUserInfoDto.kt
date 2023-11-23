package com.sudo248.sudoo.data.dto.order

import com.sudo248.base_android.base.ItemDiff
import com.sudo248.sudoo.domain.entity.order.OrderStatus
import com.sudo248.sudoo.domain.entity.order.OrderSupplier
import java.time.LocalDateTime


data class OrderSupplierUserInfoDto(
    private val orderSupplierId: String,
    private val supplierId: String,
    private val supplierName: String,
    private val supplierAvatar: String,
    private val supplierBrand: String,
    private val supplierContactUrl: String,
    private val status: OrderStatus,
    private val expectedReceiveDateTime: LocalDateTime,
    private val totalPrice: Double = 0.0,
    private val orderCartProducts: List<OrderCartProductDto>,
): ItemDiff {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other is OrderSupplier && other == this
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is OrderSupplier && other.orderSupplierId == orderSupplierId
    }

}