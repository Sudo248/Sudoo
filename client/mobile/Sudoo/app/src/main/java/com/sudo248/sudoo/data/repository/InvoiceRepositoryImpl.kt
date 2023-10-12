package com.sudo248.sudoo.data.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.sudoo.data.api.invoice.InvoiceService
import com.sudo248.sudoo.data.dto.invoice.InvoiceAddDto
import com.sudo248.sudoo.data.ktx.data
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.data.mapper.toInvoice
import com.sudo248.sudoo.domain.entity.invoice.Invoice
import com.sudo248.sudoo.domain.repository.InvoiceRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InvoiceRepositoryImpl @Inject constructor(
    private val invoiceService: InvoiceService,
    private val ioDispatcher: CoroutineDispatcher
) : InvoiceRepository {

    override suspend fun createInvoice(cartId: String): DataState<String, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(invoiceService.createInvoice(InvoiceAddDto(cartId = cartId)))
        if (response.isSuccess) {
            response.data().invoiceId!!
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getInvoiceById(invoiceId: String): DataState<Invoice, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(invoiceService.getInvoiceById(invoiceId))
        if (response.isSuccess) {
            response.data().toInvoice()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun updatePromotion(invoiceId: String, promotionId: String): DataState<Invoice, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(invoiceService.updatePromotion(invoiceId, promotionId))
        if (response.isSuccess) {
            response.data().toInvoice()
        } else {
            throw response.error().errorBody()
        }
    }
}