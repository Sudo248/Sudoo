package com.sudoo.domain.base

import com.sudoo.domain.exception.ApiException
import com.sudoo.domain.utils.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

interface BaseController {
    suspend fun <T> handle(block: suspend CoroutineScope.() -> T?): ResponseEntity<BaseResponse<*>>
    suspend fun <T> handleOn(
        dispatcher: CoroutineDispatcher,
        block: suspend CoroutineScope.() -> T
    ): ResponseEntity<BaseResponse<*>>
}

class SudooController : BaseController {
    override suspend fun <T> handle(block: suspend CoroutineScope.() -> T?): ResponseEntity<BaseResponse<*>> {
        try {
            return BaseResponse.ok(coroutineScope(block))
        } catch (e: Exception) {
            Logger.error(message = e.message, throwable = e)
            if (e is ApiException) {
                return e.getResponseEntity()
            }
            return BaseResponse.status<Any>(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
        }
    }

    override suspend fun <T> handleOn(
        dispatcher: CoroutineDispatcher,
        block: suspend CoroutineScope.() -> T
    ): ResponseEntity<BaseResponse<*>> {
        try {
            return BaseResponse.ok(withContext(dispatcher,block))
        } catch (e: Exception) {
            Logger.error(message = e.message, throwable = e)
            if (e is ApiException) {
                return e.getResponseEntity()
            }
            return BaseResponse.status<Any>(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
        }
    }
}
