package com.sudo248.sudoo.data.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.sudoo.data.api.payment.request.PaymentRequest
import com.sudo248.sudoo.data.api.payment.PaymentService
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.data.mapper.toPayment
import com.sudo248.sudoo.domain.entity.payment.Payment
import com.sudo248.sudoo.domain.repository.PaymentRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentRepositoryImpl @Inject constructor(
    private val paymentService: PaymentService,
    private val ioDispatcher: CoroutineDispatcher,
) : PaymentRepository {
    override suspend fun payWithVnPay(payment: Payment): DataState<Payment, Exception> = stateOn(ioDispatcher) {
        val request = PaymentRequest(
            paymentType = payment.paymentType.value,
            bankCode = payment.bankCode,
            orderId = payment.orderId,
            orderType = payment.orderType,
            amount = payment.amount,
            timeZoneId = TimeZone.getDefault().id
        )
        val response = handleResponse(paymentService.pay(request))
        if (response.isSuccess) {
            response.get().data!!.toPayment()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun payWithCOD(payment: Payment): DataState<Payment, Exception> = stateOn(ioDispatcher){
        TODO("Not yet implemented")
    }
}