package com.sudo248.sudoo.data.dto.auth


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:27 - 04/03/2023
 */
data class TokenDto(
    val token: String,
    val refreshToken: String? = null,
    val userId: String? = null,
)

