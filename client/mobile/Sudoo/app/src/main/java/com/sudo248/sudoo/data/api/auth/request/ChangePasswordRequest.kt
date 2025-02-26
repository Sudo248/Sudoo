package com.sudo248.sudoo.data.api.auth.request


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:37 - 04/03/2023
 */
data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String,
)