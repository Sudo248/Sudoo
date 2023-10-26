package com.sudo248.sudoo.data.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.sudoo.data.api.order.OrderService
import com.sudo248.sudoo.data.dto.order.UpsertOrderDto
import com.sudo248.sudoo.data.ktx.data
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.data.mapper.toOrder
import com.sudo248.sudoo.domain.entity.order.Order
import com.sudo248.sudoo.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(
    private val orderService: OrderService,
    private val ioDispatcher: CoroutineDispatcher
) : OrderRepository {

    override suspend fun createOrder(cartId: String): DataState<String, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(orderService.createOrder(UpsertOrderDto(cartId = cartId)))
        if (response.isSuccess) {
            response.data().orderId!!
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getOrderById(invoiceId: String): DataState<Order, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(orderService.getOrderById(invoiceId))
        if (response.isSuccess) {
            response.data().toOrder()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun updatePromotion(invoiceId: String, promotionId: String): DataState<Order, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(orderService.updatePromotion(invoiceId, promotionId))
        if (response.isSuccess) {
            response.data().toOrder()
        } else {
            throw response.error().errorBody()
        }
    }
}