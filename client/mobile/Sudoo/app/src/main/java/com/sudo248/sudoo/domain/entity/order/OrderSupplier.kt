package com.sudo248.sudoo.domain.entity.order

import com.sudo248.base_android.base.ItemDiff
import com.sudo248.sudoo.domain.entity.discovery.SupplierInfo
import com.sudo248.sudoo.domain.entity.promotion.Promotion

data class OrderSupplier(
    val orderSupplierId: String,
    val supplier: SupplierInfo,
    val promotion: Promotion?,
    val shipment: Shipment,
    val totalPrice: Double,
    val orderCartProducts: List<OrderCartProduct>
) : ItemDiff {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other is OrderSupplier && other == this
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is OrderSupplier && other.orderSupplierId == orderSupplierId
                && other.supplier.supplierId == this.supplier.supplierId
                && orderCartProducts.size == other.orderCartProducts.size
    }

}