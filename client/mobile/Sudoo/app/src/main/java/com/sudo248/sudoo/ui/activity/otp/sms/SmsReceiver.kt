package com.sudo248.sudoo.ui.activity.otp.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 22:30 - 13/03/2023
 */
class SmsReceiver (private val smsListener: SmsListener): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
            Log.d("Sudoo", "onReceive: sms")
            intent.extras?.let { bundle ->
                val smsRetrieverStatus = bundle.get(SmsRetriever.EXTRA_STATUS) as Status
                when (smsRetrieverStatus.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val smsIntent = bundle.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                        Log.d("Sudoo", "onReceive: $smsIntent")
                        if (smsIntent != null) {
                            smsListener.onReceiveSms(smsIntent)
                        } else {
                            smsListener.onFailedToReceiveSms()
                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        smsListener.onFailedToReceiveSms()
                    }
                }
            }
        }
    }
}