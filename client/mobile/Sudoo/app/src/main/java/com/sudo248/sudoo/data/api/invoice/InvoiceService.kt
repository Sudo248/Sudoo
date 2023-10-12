package com.sudo248.sudoo.data.api.invoice

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.data.dto.invoice.InvoiceAddDto
import com.sudo248.sudoo.data.dto.invoice.InvoiceDto
import com.sudo248.sudoo.domain.common.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

@ApiService(baseUrl = BuildConfig.BASE_URL)
@EnableAuthentication(Constants.Key.TOKEN)
@LoggingLever(level = Level.BODY)
interface InvoiceService {

    @POST("invoice/add")
    suspend fun createInvoice(@Body invoiceAddDto: InvoiceAddDto): Response<BaseResponse<InvoiceAddDto>>

    @GET("invoice/{invoiceId}")
    suspend fun getInvoiceById(@Path("invoiceId") invoiceId: String): Response<BaseResponse<InvoiceDto>>

    @PUT("invoice/{invoiceId}/update/promotion/{promotionId}")
    suspend fun updatePromotion(@Path("invoiceId") invoiceId: String, @Path("promotionId") promotionId: String): Response<BaseResponse<InvoiceDto>>

}