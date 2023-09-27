package com.sudoo.authservice.service.impl

import com.sudoo.authservice.dto.TokenDto
import com.sudoo.authservice.dto.VerifyDto
import com.sudoo.authservice.exception.EmailOrPhoneNumberInvalidException
import com.sudoo.authservice.repository.AccountRepository
import com.sudoo.authservice.service.OtpService
import com.sudoo.authservice.service.UserService
import com.sudoo.authservice.utils.OtpUtils
import com.sudoo.authservice.utils.TokenUtils
import com.sudoo.domain.exception.BadRequestException
import com.sudoo.domain.validator.AuthValidator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class OtpServiceImpl(
    @Qualifier("Email") private val emailUtils: OtpUtils,
    @Qualifier("SMS") private val smsUtils: OtpUtils,
    private val userService: UserService,
    private val accountRepository: AccountRepository,
    private val tokenUtils: TokenUtils,
) : OtpService {
    override suspend fun generateOtp(emailOrPhoneNumber: String): Boolean =
        getOtpUtils(emailOrPhoneNumber).generateOtp(emailOrPhoneNumber)

    override suspend fun verifyOtp(verifyDto: VerifyDto): TokenDto? {
        if (getOtpUtils(verifyDto.emailOrPhoneNumber).verifyOtp(verifyDto)) {
            val account = accountRepository.getAccountByEmailOrPhoneNumber(verifyDto.emailOrPhoneNumber)
                ?: throw EmailOrPhoneNumberInvalidException()

            return if (account.isValidated) {
                //TODO("Change password here after verify otp")
                null
            } else {
                userService.createUserForAccount(account)
                accountRepository.validate(account.userId)
                val token = tokenUtils.generateToken(account.userId)
                val refreshToken = tokenUtils.generateRefreshToken(token)
                TokenDto(token, refreshToken)
            }
        } else {
            throw BadRequestException("Verify failed")
        }
    }


    private fun getOtpUtils(emailOrPhoneNumber: String): OtpUtils {
        return when {
            AuthValidator.validatePhoneNumber(emailOrPhoneNumber) -> smsUtils
            AuthValidator.validateEmail(emailOrPhoneNumber) -> emailUtils
            else -> throw EmailOrPhoneNumberInvalidException()
        }
    }
}