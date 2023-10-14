package com.sudo248.sudoo.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import com.facebook.FacebookSdk
import com.google.firebase.messaging.FirebaseMessaging
import com.sudo248.base_android.app.BaseApplication
import com.sudo248.base_android.utils.SharedPreferenceUtils
import com.sudo248.sudoo.domain.common.Constants
import dagger.hilt.android.HiltAndroidApp


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 00:50 - 05/03/2023
 */

@HiltAndroidApp
class SudooApplication : BaseApplication() {
    override val enableNetworkObserver: Boolean = true
    override fun onCreate() {
        super.onCreate()
        SharedPreferenceUtils.createApplicationSharePreference(this)
        FacebookSdk.setApplicationId("6161985857254099")
        setupNotification()
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.Notification.PROMOTION_TOPIC)
    }

    private fun setupNotification() {

        val important = NotificationManager.IMPORTANCE_DEFAULT

        val promotionChannel = NotificationChannel(
            Constants.Notification.PROMOTION_NOTIFICATION_CHANNEL_ID,
            Constants.Notification.PROMOTION_NOTIFICATION_CHANNEL_NAME,
            important
        )
        promotionChannel.description = "This notification alert when merchant add new or update a promotion"
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(promotionChannel)

        val messageChannel = NotificationChannel(
            Constants.Notification.MESSAGE_NOTIFICATION_CHANNEL_ID,
            Constants.Notification.MESSAGE_NOTIFICATION_CHANNEL_NAME,
            important
        )
        messageChannel.description = "This notification alert when new message"
        manager.createNotificationChannel(messageChannel)
    }
}