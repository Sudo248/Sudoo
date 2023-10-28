package com.sudo248.sudoo.domain.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.sudoo.domain.entity.order.Order

interface OrderRepository {
    suspend fun createOrder(cartId: String): DataState<String, Exception>
    suspend fun getOrderById(invoiceId: String): DataState<Order, Exception>
    suspend fun updatePromotion(invoiceId: String, promotionId: String): DataState<Order, Exception>
}