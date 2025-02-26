package com.sudo248.sudoo.ui.activity.splash

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.ktx.createActionIntentDirections
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.base_android.navigation.IntentDirections
import com.sudo248.base_android.utils.SharedPreferenceUtils
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.repository.AuthRepository
import com.sudo248.sudoo.ui.activity.auth.AuthActivity
import com.sudo248.sudoo.ui.activity.main.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 08:12 - 06/03/2023
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<IntentDirections>() {

    var viewController: ViewController? = null
    private var jobFetchConfig: Job? = null

    init {
        launch {
            val startTime = System.currentTimeMillis()
            calculateCounter()
            jobFetchConfig = fetchConfig()
            val result = authRepository.refreshToken()
            jobFetchConfig?.join()
            delayFrom(startTime).join()
            if (result.isSuccess) {
                navigator.navigateOff(MainActivity::class.createActionIntentDirections())
            } else {
                navigator.navigateOff(AuthActivity::class.createActionIntentDirections())
            }

//            navigator.navigateOff(OtpActivity::class.createActionIntentDirections{
//                putExtra(Constants.Key.PHONE_NUMBER, "0989465270")
//            })
//            navigator.navigateOff(MainActivity::class.createActionIntentDirections())
//            navigator.navigateOff(PaymentActivity::class.createActionIntentDirections())
        }
    }

    private fun calculateCounter() {
        var counter = SharedPreferenceUtils.getInt(Constants.Key.COUNTER, 0)
        if (counter > 0 && counter % Constants.TIMES_REFRESH_CONFIG == 0) {
            counter = 0
        } else {
            counter += 1
        }
        SharedPreferenceUtils.putInt(Constants.Key.COUNTER, counter)
    }

    private fun fetchConfig() = launch {
        fetchAuthConfig()
    }

    private suspend fun fetchAuthConfig() = viewModelScope.launch {
        authRepository.getAuthConfig()
    }

    private suspend fun delayFrom(startTime: Long, delay: Long = 500L) = launch {
        val currentTime = System.currentTimeMillis()
        if (currentTime - startTime < delay) {
            delay(delay - (currentTime - startTime))
        }
    }
}