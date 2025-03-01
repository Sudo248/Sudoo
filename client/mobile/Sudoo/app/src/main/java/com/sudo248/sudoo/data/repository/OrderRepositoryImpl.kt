package com.sudo248.sudoo.data.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.sudoo.data.api.order.OrderService
import com.sudo248.sudoo.data.dto.order.PatchOrderSupplierDto
import com.sudo248.sudoo.data.dto.order.UpsertOrderDto
import com.sudo248.sudoo.data.ktx.data
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.data.mapper.toOrder
import com.sudo248.sudoo.data.mapper.toUpsertOrderPromotion
import com.sudo248.sudoo.data.mapper.toUpsertOrderPromotionDto
import com.sudo248.sudoo.domain.entity.order.Order
import com.sudo248.sudoo.domain.entity.order.OrderStatus
import com.sudo248.sudoo.domain.entity.order.OrderSupplierInfo
import com.sudo248.sudoo.domain.entity.order.UpsertOrderPromotion
import com.sudo248.sudoo.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(
    private val orderService: OrderService,
    private val ioDispatcher: CoroutineDispatcher
) : OrderRepository {

    override suspend fun createOrder(cartId: String): DataState<Order, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(orderService.createOrder(UpsertOrderDto(cartId = cartId)))
            if (response.isSuccess) {
                response.data().toOrder()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun getOrderById(orderId: String): DataState<Order, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(orderService.getOrderById(orderId))
            if (response.isSuccess) {
                response.data().toOrder()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun updatePromotion(
        orderId: String,
        upsertOrderPromotion: UpsertOrderPromotion
    ): DataState<UpsertOrderPromotion, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(
            orderService.updatePromotion(
                orderId,
                upsertOrderPromotion.toUpsertOrderPromotionDto()
            )
        )
        if (response.isSuccess) {
            response.data().toUpsertOrderPromotion()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun cancelOrderById(orderId: String): DataState<Boolean, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(
            orderService.cancelOrderById(orderId)
        )
        if (response.isSuccess) {
            true
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getListOrderSupplierByStatus(status: String): DataState<List<OrderSupplierInfo>, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(
                orderService.getListOrderSupplierByStatus(status)
            )
            if (response.isSuccess) {
                response.data()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun getOrderSupplierDetail(orderSupplierId: String): DataState<Order, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(orderService.getOrderSupplierDetail(orderSupplierId))
            if (response.isSuccess) {
                response.data().toOrder()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun patchOrderSupplier(
        orderSupplierId: String,
        patchOrderSupplier: PatchOrderSupplierDto
    ): DataState<Boolean, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(orderService.patchOrderSupplier(orderSupplierId, patchOrderSupplier))
        if (response.isSuccess) {
            true
        } else {
            throw response.error().errorBody()
        }
    }
}