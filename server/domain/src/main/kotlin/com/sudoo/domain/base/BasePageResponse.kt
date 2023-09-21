package com.sudoo.domain.base

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class BasePageResponse<out D>(
    val statusCode: Int,
    val success: Boolean = (statusCode == 200) || (statusCode == 201),
    val message: String?,
    val data: D?,
    val pagination: Pagination,
) {
    companion object {
        fun <Data> ok(
            body: Data,
            pagination: Pagination,
            message: String? = null
        ): ResponseEntity<BasePageResponse<*>> = ResponseEntity.ok(
            BasePageResponse(
                statusCode = HttpStatus.OK.value(),
                message = message ?: HttpStatus.OK.reasonPhrase,
                data = body,
                pagination = pagination
            )
        )

        fun <Data : Any> status(
            status: HttpStatus,
            message: String? = null,
            body: Data? = null,
            pagination: Pagination = Pagination()
        ): ResponseEntity<BasePageResponse<*>> = ResponseEntity.status(status).body(
            BasePageResponse(
                statusCode = status.value(),
                message = message ?: status.reasonPhrase,
                data = body,
                pagination = pagination
            )
        )
    }
}