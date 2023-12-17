package com.sudo248.sudoo.domain.entity.auth


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:03 - 04/03/2023
 */
enum class Provider(val value: String) {
    AUTH_SERVICE("Auth-service"),
    GOOGLE("Google"),
    FACEBOOK("Facebook"),
    PHONE("emailOrPhoneNumber"),
}