package com.sudo248.sudoo.data.repository

import android.util.Log
import com.google.gson.Gson
import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.ktx.state
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.base_android.utils.SharedPreferenceUtils
import com.sudo248.sudoo.data.api.auth.AuthService
import com.sudo248.sudoo.data.api.auth.request.AccountRequest
import com.sudo248.sudoo.data.api.auth.request.ChangePasswordRequest
import com.sudo248.sudoo.data.api.auth.request.OtpRequest
import com.sudo248.sudoo.data.api.notification.NotificationService
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.data.mapper.toToken
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.auth.Account
import com.sudo248.sudoo.domain.entity.auth.AuthConfig
import com.sudo248.sudoo.domain.entity.auth.Token
import com.sudo248.sudoo.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 00:32 - 05/03/2023
 */

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val notificationService: NotificationService,
    private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {
    private val gson = Gson()
    override suspend fun getAuthConfig(): DataState<AuthConfig, Exception> = stateOn(ioDispatcher) {
        val counter = SharedPreferenceUtils.getInt(Constants.Key.COUNTER, 0)
        val localConfig = getLocalConfig()
        if (counter % Constants.TIMES_REFRESH_CONFIG == 0 || localConfig == null) {
            val response = handleResponse(authService.getAuthConfig())
            if (response.isSuccess) {
                response.get().data!!.also {
                    saveLocalConfig(it)
                }
            } else {
                AuthConfig(enableOtp = false)
            }
        } else {
            localConfig
        }
    }

    override suspend fun refreshToken(): DataState<Token, Exception> = stateOn(ioDispatcher) {
        val refreshToken = SharedPreferenceUtils.withApplicationSharedPreference().getString(Constants.Key.REFRESH_TOKEN, "")
        if (refreshToken.isEmpty()) {
            throw Exception()
        } else {
            val response = handleResponse(authService.refreshToken(refreshToken))
            if (response.isSuccess) {
                val token = response.get().data!!.toToken()
                saveToken(token)
                token
            } else {
                throw response.error().errorBody()
            }
        }
    }

    override suspend fun saveToken(token: Token): DataState<Unit, Exception> = state {
        Log.d("Sudoo", "saveToken: $token")
        SharedPreferenceUtils.withApplicationSharedPreference().run {
            putString(Constants.Key.TOKEN, token.token)
            putString(Constants.Key.REFRESH_TOKEN, token.refreshToken.orEmpty())
        }
        val fcmToken = SharedPreferenceUtils.getString(Constants.Key.FCM_TOKEN, "")
        if (fcmToken.isNotEmpty()) {
            notificationService.saveToken(fcmToken)
        }
    }

    override suspend fun signIn(account: Account): DataState<Token, Exception> =
        stateOn(ioDispatcher) {
            val request = AccountRequest(
                phoneNumber = account.phoneNumber,
                password = account.password,
                provider = account.provider
            )
            val response = handleResponse(authService.signIn(request))
            if (response.isSuccess) {
                val data = response.get().data!!
                /*data.userId?.let {
                    SharedPreferenceUtils.putString(Constants.Key.USER_ID, it)
                }*/
                val token = data.toToken()
                saveToken(token)
                token
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun signUp(account: Account): DataState<Unit, Exception> =
        stateOn(ioDispatcher) {
            val request = AccountRequest(
                phoneNumber = account.phoneNumber,
                password = account.password,
                provider = account.provider
            )
            val response = handleResponse(authService.signUp(request))
            if (response.isSuccess) {
                /*response.get().data?.let {
                    SharedPreferenceUtils.putString(Constants.Key.USER_ID, it)
                }*/
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun generateOtp(phoneNumber: String): DataState<Unit, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(authService.generateOtp(phoneNumber))
            if (response.isError) {
                throw response.error().errorBody()
            }
        }

    override suspend fun verifyOtp(phoneNumber: String, otp: String): DataState<Token, Exception> =
        stateOn(ioDispatcher) {
            val request = OtpRequest(
                phoneNumber,
                otp
            )
            val response = handleResponse(authService.verifyOtp(request))
            if (response.isSuccess) {
                val token = response.get().data!!.toToken()
                saveToken(token)
                token
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun changePassword(
        oldPassword: String,
        newPassWord: String
    ): DataState<Unit, Exception> = stateOn(ioDispatcher) {
        val request = ChangePasswordRequest(
            oldPassword,
            newPassWord
        )
        val response = handleResponse(authService.changePassword(request))
        if (response.isError) {
            throw response.error().errorBody()
        }
    }

    override suspend fun logout(): DataState<Unit, Exception> = stateOn(ioDispatcher) {
        authService.logout()
    }

    private suspend fun getLocalConfig(): AuthConfig? {
        val stringConfig = SharedPreferenceUtils.getStringAsync(Constants.Key.AUTH_CONFIG)
        if (stringConfig.isEmpty()) return null
        return gson.fromJson(stringConfig, AuthConfig::class.java)
    }

    private suspend fun saveLocalConfig(config: AuthConfig) {
        val stringConfig = gson.toJson(config)
        SharedPreferenceUtils.putStringAsync(Constants.Key.AUTH_CONFIG, stringConfig)
    }
}