package com.sudoo.domain.base

import com.sudoo.domain.exception.ApiException
import com.sudoo.domain.utils.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

abstract class BaseController {
    suspend fun <T> handle(block: suspend () -> T?): ResponseEntity<BaseResponse<*>> {
        try {
            return BaseResponse.ok(block.invoke())
        } catch (e: Exception) {
            Logger.error(message = e.message, throwable = e)
            if (e is ApiException) {
                return e.getResponseEntity()
            }
            return BaseResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, e.message, body = null)
        }
    }

    suspend fun <T> handleOn(
        dispatcher: CoroutineDispatcher,
        block: suspend () -> T
    ): ResponseEntity<BaseResponse<*>> {
        try {
            return BaseResponse.ok(withContext(dispatcher) { block.invoke() })
        } catch (e: Exception) {
            Logger.error(message = e.message, throwable = e)
            if (e is ApiException) {
                return e.getResponseEntity()
            }
            return BaseResponse.status<Any>(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
        }
    }
}
