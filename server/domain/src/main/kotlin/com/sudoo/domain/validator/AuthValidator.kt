package com.sudoo.domain.validator

object AuthValidator {
    private const val PHONE_NUMBER_PATTERN = "(84|0[3|5|7|8|9])+([0-9]{8})\b"
    private const val EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})\$"

    fun validatePhoneNumber(phoneNumber: String) = phoneNumber.matches(PHONE_NUMBER_PATTERN.toRegex())
    fun validateEmail(email: String) = email.matches(EMAIL_PATTERN.toRegex())
    fun validatePassword(password: String) = password.length >= 8

}