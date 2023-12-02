package com.sudoo.shipment.ui

import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.createActionIntentDirections
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.base_android.navigation.IntentDirections
import com.sudo248.base_android.utils.SharedPreferenceUtils
import com.sudoo.shipment.BuildConfig
import com.sudoo.shipment.Constants
import com.sudoo.shipment.api.AuthService
import com.sudoo.shipment.api.request.AccountRequest
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authService: AuthService
) : BaseViewModel<IntentDirections>() {

    fun signIn() = launch(Dispatchers.IO) {
        handleResponse(
            authService.signIn(
                AccountRequest(
                    phoneNumber = BuildConfig.USER_NAME,
                    password = BuildConfig.PASSWORD
                )
            )
        )
            .onSuccess {
                SharedPreferenceUtils.putString(Constants.TOKEN, it.data!!.token)
                navigator.navigateOff(ScanQrActivity::class.createActionIntentDirections())
            }
            .onError {
                it.printStackTrace()
            }
    }

}