package com.sudo248.sudoo.data.api.auth.request

import com.sudo248.sudoo.domain.entity.auth.Provider


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:21 - 04/03/2023
 */
data class AccountRequest(
    val phoneNumber: String,
    val password: String,
    val provider: Provider? = null
)