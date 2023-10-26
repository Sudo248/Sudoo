package com.sudo248.sudoo.ui.activity.payment

import androidx.activity.viewModels
import com.sudo248.base_android.base.BaseActivity
import com.sudo248.sudoo.databinding.ActivityPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentActivity : BaseActivity<ActivityPaymentBinding, PaymentViewModel>() {
    override val viewModel: PaymentViewModel by viewModels()

    override fun initView() {

    }
}