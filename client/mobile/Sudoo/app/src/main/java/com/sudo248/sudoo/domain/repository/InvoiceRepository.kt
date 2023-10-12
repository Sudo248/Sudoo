package com.sudo248.sudoo.domain.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.sudoo.domain.entity.invoice.Invoice

interface InvoiceRepository {
    suspend fun createInvoice(cartId: String): DataState<String, Exception>
    suspend fun getInvoiceById(invoiceId: String): DataState<Invoice, Exception>
    suspend fun updatePromotion(invoiceId: String, promotionId: String): DataState<Invoice, Exception>
}