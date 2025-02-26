package com.sudo248.sudoo.ui.activity.main.fragment.payment_result

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.visible
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.FragmentPaymentResultBinding

class PaymentResultFragment : BaseFragment<FragmentPaymentResultBinding, PaymentResultViewModel>() {

    override val viewModel: PaymentResultViewModel by viewModels()

    private val args: PaymentResultFragmentArgs by navArgs()

    override fun initView() {
        if (args.paymentResultSuccess) {
            setupSuccessView()
        } else {
            setupFailView()
        }
    }

    private fun setupSuccessView() {
        binding.groupPaymentSuccess.visible()
        binding.groupPaymentFail.gone()
        binding.txtNextAction.text = getString(R.string.check_order)
        binding.txtNextAction.setOnClickListener {
            navigateOffAll(
                PaymentResultFragmentDirections.actionPaymentResultFragmentToOrderStatusFragment(
                    isHideBottomNav = true
                )
            )
        }
    }
    private fun setupFailView() {
        binding.groupPaymentSuccess.gone()
        binding.groupPaymentFail.visible()
        binding.txtNextAction.text = getString(R.string.continue_buy)
        binding.txtNextAction.setOnClickListener {
            navigateOffAll(PaymentResultFragmentDirections.actionPaymentResultFragmentToDiscoveryFragment())
        }
    }

}