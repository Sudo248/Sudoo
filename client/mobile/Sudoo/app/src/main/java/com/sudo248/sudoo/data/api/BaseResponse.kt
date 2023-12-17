package com.sudo248.sudoo.data.api

import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.exception.ApiException


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:11 - 04/03/2023
 */
open class BaseResponse<Data> {
    var statusCode: Int = 0
    var success: Boolean = false
    var message: String = ""
    var data: Data? = null

    override fun toString(): String {
        return "{ statusCode: $statusCode\n" +
                "success: $success\n" +
                "message: $message\n" +
                "data: $data" +
                "}"
    }
}