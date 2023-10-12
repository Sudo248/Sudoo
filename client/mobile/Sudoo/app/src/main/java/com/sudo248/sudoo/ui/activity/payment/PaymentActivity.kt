package com.sudo248.sudoo.ui.activity.payment

import android.app.Dialog
import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sudo248.base_android.base.BaseActivity
import com.sudo248.base_android.ktx.showWithTransparentBackground
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ActivityPaymentBinding
import com.sudo248.sudoo.databinding.DialogChoosePaymentMethodBinding
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.invoice.Invoice
import com.sudo248.sudoo.ui.activity.main.adapter.PaymentAdapter
import com.sudo248.sudoo.ui.activity.payment.fragment.ViewController
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import com.sudo248.sudoo.ui.util.Utils
import com.vnpay.authentication.VNP_AuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentActivity : BaseActivity<ActivityPaymentBinding, PaymentViewModel>() {
    override val viewModel: PaymentViewModel by viewModels()

    override fun initView() {

    }
}