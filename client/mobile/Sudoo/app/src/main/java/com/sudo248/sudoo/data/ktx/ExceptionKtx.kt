package com.sudo248.sudoo.data.ktx

import com.google.android.gms.common.api.Api
import com.sudo248.base_android.exception.ApiException
import com.sudo248.sudoo.data.api.BaseResponse


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 07:45 - 06/03/2023
 */

fun ApiException.Companion.fromResponse(response: BaseResponse<*>): ApiException {
    return ApiException(
        statusCode = response.statusCode,
        message = response.message,
        data = response.data
    )
}

fun ApiException.errorBody(): ApiException {
    return if (data != null && data is BaseResponse<*>) {
        val errorBody = data as BaseResponse<*>
        ApiException.fromResponse(errorBody)
    } else {
        this
    }
}