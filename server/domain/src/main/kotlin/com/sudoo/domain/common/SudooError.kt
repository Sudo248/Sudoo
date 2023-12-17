package com.sudoo.domain.common

enum class SudooError(val message: String) {
    EMAIL_OR_PHONE_NUMBER_INVALID("Email or Phone number invalid"),
    EMAIL_OR_PHONE_NUMBER_EXISTED("Email or Phone number existed"),
    EMAIL_OR_PHONE_NUMBER_NOT_VALIDATED("Email or Phone number is not validated"),
    PASSWORD_INVALID("Password length must be 8"),
    WRONG_PASSWORD("Wrong password"),
    UNAUTHORIZED("Header missing ${Constants.AUTHORIZATION}"),
    TOKEN_INVALID("Token is invalid"),
    TOKEN_EXPIRE("Token is expired"),
    TOKEN_UNSUPPORTED("Token unsupported"),
    TOKEN_CLAIM_EMPTY("JWT claims string is empty"),
    MUST_CONTAIN_TOKEN_TYPE("Token must contain ${Constants.TOKEN_TYPE}"),
    INTERNAL_API_NOT_ALLOW("Not allow access internal api"),
}