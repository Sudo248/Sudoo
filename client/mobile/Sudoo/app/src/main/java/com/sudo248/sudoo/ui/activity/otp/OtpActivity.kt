package com.sudo248.sudoo.ui.activity.otp

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.sudo248.base_android.base.BaseActivity
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.base_android.utils.KeyBoardUtils
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ActivityOtpBinding
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.ui.activity.otp.sms.SmsListener
import com.sudo248.sudoo.ui.activity.otp.sms.SmsReceiver
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpActivity : BaseActivity<ActivityOtpBinding, OtpViewModel>() {
    override val layoutId: Int = R.layout.activity_otp
    override val viewModel: OtpViewModel by viewModels()
    override val enableStateScreen: Boolean = true

    private val smsPopupLaunch =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val message = result.data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                val otp = viewModel.getOtpOrNull(message, Constants.PATTERN_OTP)
                otp?.let {
                    binding.edtOtp.setText(it)
                }
            }
        }

    private val smsReceiver = SmsReceiver(object : SmsListener {
        override fun onReceiveSms(smsIntent: Intent) {
            smsPopupLaunch.launch(smsIntent)
        }

        override fun onFailedToReceiveSms() {

        }
    })

    override fun initView() {
        registerSmsReceive()
        binding.viewModel = viewModel
        val phoneNumber = intent.getStringExtra(Constants.Key.PHONE_NUMBER)
        viewModel.setSmsRetrieverClient(SmsRetriever.getClient(this))
        viewModel.setPhoneNumber(phoneNumber ?: "")
        binding.edtOtp.setOnFulFillListener {
            viewModel.onFullFillListener(it)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterSmsReceive()
    }

    override fun onStateError() {
        super.onStateError()
        DialogUtils.showErrorDialog(
            context = this,
            message = viewModel.errorMessage ?: getString(R.string.unexpected_error)
        )
    }

    private fun registerSmsReceive() {
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsReceiver, intentFilter)
    }

    private fun unregisterSmsReceive() {
        unregisterReceiver(smsReceiver)
    }

    override fun onPause() {
        super.onPause()
        KeyBoardUtils.hide(this)
    }
}