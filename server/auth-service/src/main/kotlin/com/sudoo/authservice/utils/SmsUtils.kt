package com.sudoo.authservice.utils

import com.sudoo.authservice.dto.VerifyDto
import com.sudoo.domain.exception.ApiException
import com.twilio.Twilio
import com.twilio.rest.verify.v2.service.Verification
import com.twilio.rest.verify.v2.service.VerificationCheck
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
@Qualifier("SMS")
class SmsUtils : OtpUtils{

    @Value("\${otp.timeout}")
    private var otpTimeout: Long = 0

    @Value("\${twilio.account-sid}")
    private lateinit var twilioAccountSid: String

    @Value("\${twilio.auth-token}")
    private lateinit var twilioAuthToken: String

    @Value("\${twilio.verify}")
    private lateinit var verify: String

    override suspend fun generateOtp(emailOrPhoneNumber: String): Boolean {
        Twilio.init(twilioAccountSid, twilioAuthToken)
        try {
            val verification = Verification.creator(
                verify,
                formatPhoneNumber(emailOrPhoneNumber),
                "sms"
            ).create()
            println("Verification status: ${verification.status}")
        }catch (e: Exception) {
            throw ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error from twilio: ${e.message}")
        }
        return true
    }

    override suspend fun verifyOtp(verifyDto: VerifyDto): Boolean {
        Twilio.init(twilioAccountSid, twilioAuthToken)
        try {
            VerificationCheck.creator(
                verify,
                verifyDto.otp
            )
                .setTo(formatPhoneNumber(verifyDto.emailOrPhoneNumber))
                .create()
        } catch (e: Exception) {
            throw ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error from twilio: ${e.message}")
        }
        return true
    }

    private fun formatPhoneNumber(phoneNumber: String): String {
        return if (phoneNumber.startsWith("0")) {
            "+84${phoneNumber.substring(1)}"
        } else if (phoneNumber.startsWith("+84")) {
            phoneNumber
        } else throw ApiException(HttpStatus.BAD_REQUEST, "Phone number invalid")
    }
}