package com.sudoo.domain.base

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.Serializable

data class BaseResponse<out D>(
    val statusCode: Int,
    val success: Boolean = (statusCode == 200) || (statusCode == 201),
    val message: String?,
    val data: D?
) {

    constructor() : this(statusCode = 0, success = true, message = null, data = null)
    companion object {
        fun <Data> ok(body: Data, message: String? = null): ResponseEntity<BaseResponse<*>> = ResponseEntity.ok(
            BaseResponse(
                statusCode = HttpStatus.OK.value(),
                message = message ?: HttpStatus.OK.reasonPhrase,
                data = body
            )
        )

        fun <Data : Any> status(
            status: HttpStatus,
            message: String? = null,
            body: Data? = null,
        ): ResponseEntity<BaseResponse<*>> = ResponseEntity.status(status).body(
            BaseResponse(
                statusCode = status.value(),
                message = message ?: status.reasonPhrase,
                data = body
            )
        )
    }
}