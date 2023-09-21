package com.sudoo.authservice.utils

import com.sudoo.authservice.controller.dto.VerifyDto
import com.sudoo.authservice.exception.OTPExpired
import org.apache.commons.lang.RandomStringUtils
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
@Qualifier("Email")
class EmailUtils (
    private val mailSender: JavaMailSender,
    private val redis: RedisTemplate<String, Any>,
) : OtpUtils{

    @Value("\${otp.timeout}")
    private var otpTimeout: Long = 0

    @Value("\${spring.mail.username}")
    private lateinit var senderEmail: String

    override suspend fun generateOtp(emailOrPhoneNumber: String): Boolean {
        val otp = RandomStringUtils.random(8)
        redis.opsForValue().set(emailOrPhoneNumber, otp, otpTimeout, TimeUnit.MILLISECONDS)
        sendOTPEmail(emailOrPhoneNumber, otp)
        return true
    }

    override suspend fun verifyOtp(verifyDto: VerifyDto): Boolean {
        val otp = redis.opsForValue().getAndDelete(verifyDto.emailOrPhoneNumber)
        if (otp is String? && !otp.isNullOrEmpty()) {
            return otp == verifyDto.otp
        }
        throw OTPExpired()
    }

    private fun sendOTPEmail(email: String, otp: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message)

        helper.apply {
            setFrom(senderEmail, "Sudoo")
            setTo(email)

            val subject = "Here's your One Time Password (OTP) - Expire in 2 minutes!"
            val content = """
                <p>Hello $email!<p>
                <p>For security reason, you're required to use the following One Time Password to verify:</p>
                <p><b> $otp <b><p>
                <br>
                <p>Note: this OTP is set to expire in 2 minutes.<p>
            """.trimIndent()

            helper.setSubject(subject)
            helper.setText(content, true)

            mailSender.send(message)
        }
    }
}