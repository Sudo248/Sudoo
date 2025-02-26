package com.sudo248.sudoo.domain.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.sudoo.domain.entity.payment.Payment

interface PaymentRepository {
    suspend fun pay(payment: Payment): DataState<Payment, Exception>
}