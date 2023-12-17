package com.sudo248.sudoo.domain.entity.order

import com.sudo248.base_android.base.ItemDiff
import java.time.LocalDateTime

data class OrderSupplierInfo(
    val orderSupplierId: String,
    val supplierId: String,
    val supplierName: String,
    val supplierAvatar: String,
    val supplierBrand: String,
    val supplierContactUrl: String,
    var status: OrderStatus,
    val expectedReceiveDateTime: LocalDateTime,
    val totalPrice: Double,
    val createdAt: LocalDateTime,
    val orderCartProduct: OrderCartProduct,
    val totalProduct: Int,
) : ItemDiff {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other is OrderSupplierInfo && other == this
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is OrderSupplierInfo &&
                other.orderSupplierId == this.orderSupplierId &&
                other.supplierId == this.supplierId &&
                other.status == this.status
    }

}