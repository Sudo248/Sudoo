package com.sudoo.shipment

import com.sudo248.base_android.app.BaseApplication
import com.sudo248.base_android.utils.SharedPreferenceUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferenceUtils.createApplicationSharePreference(this)
    }
}