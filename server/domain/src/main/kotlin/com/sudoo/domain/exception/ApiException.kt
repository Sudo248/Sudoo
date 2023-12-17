package com.sudoo.domain.exception

import com.sudoo.domain.base.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

open class ApiException(
    private val status: HttpStatus,
    message: String? = null
) : Exception(message ?: status.reasonPhrase) {

    fun getResponseEntity() = getResponseEntity(null)

    fun <T> getResponseEntity(body: T? = null): ResponseEntity<BaseResponse<*>> =
        BaseResponse.status(status, message = message, body = body)

}