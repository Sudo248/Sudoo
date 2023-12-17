package com.sudo248.sudoo.ui.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sudo248.base_android.utils.SharedPreferenceUtils
import com.sudo248.sudoo.R
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.chat.Chat
import com.sudo248.sudoo.ui.activity.main.MainActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow

class FirebaseMessageService : FirebaseMessagingService() {

    companion object {
        val chatFlow = MutableSharedFlow<Chat>()
    }

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val gson = Gson()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        SharedPreferenceUtils.putString(Constants.Key.FCM_TOKEN, token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.containsKey("chat")) {
            try {
                val chat = gson.fromJson(message.data["chat"], Chat::class.java)
                Log.d("Sudoo", "onMessageReceived: $chat")
                if (chat.sender.userId != SharedPreferenceUtils.getString(Constants.Key.USER_ID)) {
                    scope.launch {
                        chatFlow.emit(chat)
                    }
                    message.notification?.let {
                        pushNotification(
                            Constants.Notification.MESSAGE_NOTIFICATION_CHANNEL_ID,
                            Constants.Notification.MESSAGE_NOTIFICATION_ID,
                            it
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            message.notification?.let {
                pushNotification(
                    Constants.Notification.PROMOTION_NOTIFICATION_CHANNEL_ID,
                    Constants.Notification.PROMOTION_NOTIFICATION_ID,
                    it,
                    getPendingIntentMainActivity(Constants.Screen.PROMOTION)
                )
            }
        }
    }

    private fun pushNotification(
        channelId: String,
        notificationId: Int,
        notification: RemoteMessage.Notification,
        pendingIntent: PendingIntent? = null
    ) {
        val _notification = NotificationCompat.Builder(
            applicationContext,
            channelId
        ).apply {
            pendingIntent?.let {
                setContentIntent(it)
            }
            setContentTitle(notification.title)
            setContentText(notification.body)
            setSmallIcon(R.mipmap.ic_launcher_round)
            setDefaults(NotificationCompat.DEFAULT_SOUND)
            color = getColor(R.color.primaryColor)
            setCategory(NotificationCompat.CATEGORY_ALARM)

        }.build()
        (applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager).notify(
            notificationId,
            _notification
        )
    }

    private fun getPendingIntentMainActivity(openFragment: String? = null): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        openFragment?.let {
            intent.putExtra(Constants.Key.SCREEN, openFragment)
        }
        return PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}