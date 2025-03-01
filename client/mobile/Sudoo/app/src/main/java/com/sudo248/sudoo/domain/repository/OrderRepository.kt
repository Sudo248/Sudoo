package com.sudo248.sudoo.domain.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.sudoo.data.dto.order.PatchOrderSupplierDto
import com.sudo248.sudoo.domain.entity.order.Order
import com.sudo248.sudoo.domain.entity.order.OrderSupplierInfo
import com.sudo248.sudoo.domain.entity.order.UpsertOrderPromotion

interface OrderRepository {
    suspend fun createOrder(cartId: String): DataState<Order, Exception>
    suspend fun getOrderById(orderId: String): DataState<Order, Exception>
    suspend fun updatePromotion(
        orderId: String,
        upsertOrderPromotion: UpsertOrderPromotion
    ): DataState<UpsertOrderPromotion, Exception>

    suspend fun cancelOrderById(orderId: String): DataState<Boolean, Exception>
    suspend fun getListOrderSupplierByStatus(status: String): DataState<List<OrderSupplierInfo>, Exception>
    suspend fun getOrderSupplierDetail(orderSupplierId: String): DataState<Order, Exception>

    suspend fun patchOrderSupplier(
        orderSupplierId: String,
        patchOrderSupplier: PatchOrderSupplierDto
    ): DataState<Boolean, Exception>
}