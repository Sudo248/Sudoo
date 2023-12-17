package com.sudoo.authservice.service.impl

import com.sudoo.authservice.dto.ChangePasswordDto
import com.sudoo.authservice.dto.SignInDto
import com.sudoo.authservice.dto.SignUpDto
import com.sudoo.authservice.dto.TokenDto
import com.sudoo.authservice.exception.EmailOrPhoneNumberExistedException
import com.sudoo.authservice.exception.EmailOrPhoneNumberInvalidException
import com.sudoo.authservice.exception.EmailOrPhoneNumberNotValidatedException
import com.sudoo.authservice.exception.WrongPasswordException
import com.sudoo.authservice.mapper.toModel
import com.sudoo.authservice.model.Account
import com.sudoo.authservice.repository.AccountRepository
import com.sudoo.authservice.service.AccountService
import com.sudoo.authservice.service.OtpService
import com.sudoo.authservice.utils.TokenUtils
import com.sudoo.domain.exception.ApiException
import com.sudoo.domain.exception.CommonException
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val encoder: PasswordEncoder,
    private val tokenUtils: TokenUtils,
    private val otpService: OtpService,
) : AccountService {
    override suspend fun signIn(signInDto: SignInDto): TokenDto {
        val account = accountRepository.getAccountByEmailOrPhoneNumber(signInDto.emailOrPhoneNumber)
            ?: throw EmailOrPhoneNumberInvalidException()

        if (!encoder.matches(signInDto.password, account.password)) {
            throw WrongPasswordException()
        }
        if (!account.isValidated) {
            throw EmailOrPhoneNumberNotValidatedException()
        }
        val token = tokenUtils.generateToken(account.userId)
        val refreshToken = tokenUtils.generateRefreshToken(account.userId)
        return TokenDto(token, refreshToken)
    }

    override suspend fun signUp(signUpDto: SignUpDto) {
        if (accountRepository.existsByEmailOrPhoneNumber(signUpDto.emailOrPhoneNumber) == 1) {
            throw EmailOrPhoneNumberExistedException()
        }

        if (otpService.generateOtp(signUpDto.emailOrPhoneNumber)) {
            saveAccount(signUpDto.toModel())
        } else {
            throw CommonException()
        }
    }

    override suspend fun changePassword(userId: String, changePasswordDto: ChangePasswordDto): Boolean {
        val account = accountRepository.findById(userId) ?: throw CommonException()
        if (!encoder.matches(account.password, changePasswordDto.oldPassword)) {
            throw WrongPasswordException()
        }
        account.password = changePasswordDto.newPassword
        saveAccount(account)
        return true
    }

    override suspend fun logout(userId: String): Boolean {
        return true
    }

    private suspend fun saveAccount(account: Account, isHashPassword: Boolean = true) {
        try {
            if (isHashPassword) {
                account.password = encoder.encode(account.password)
            }
            accountRepository.save(account)
        } catch (e: Exception) {
            e.printStackTrace()
            throw ApiException(HttpStatus.INSUFFICIENT_STORAGE, e.message)
        }
    }
}