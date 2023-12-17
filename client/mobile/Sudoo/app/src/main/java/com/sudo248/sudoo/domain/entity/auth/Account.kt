package com.sudo248.sudoo.domain.entity.auth

import java.time.LocalDateTime


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:02 - 04/03/2023
 */
data class Account(
    val userId: String? = null,
    val phoneNumber: String,
    val password: String,
    val provider: Provider? = null,
    val isValidated: Boolean? = null,
    val createdAt: LocalDateTime? = null,
)
