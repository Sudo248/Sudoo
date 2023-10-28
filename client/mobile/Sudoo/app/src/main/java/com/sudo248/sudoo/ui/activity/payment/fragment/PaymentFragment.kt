package com.sudo248.sudoo.ui.activity.payment.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.showWithTransparentBackground
import com.sudo248.base_android.navigation.ResultCallback
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.DialogChoosePaymentMethodBinding
import com.sudo248.sudoo.databinding.FragmentPaymentBinding
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.order.Order
import com.sudo248.sudoo.domain.entity.promotion.Promotion
import com.sudo248.sudoo.ui.activity.main.MainActivity
import com.sudo248.sudoo.ui.activity.main.adapter.OrderAdapter
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import com.sudo248.sudoo.ui.util.Utils
import com.vnpay.authentication.VNP_AuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding, PaymentViewModel>(), ViewController {

    override val viewModel: PaymentViewModel by viewModels()

    override val enableStateScreen: Boolean = true

    private val adapter: OrderAdapter by lazy { OrderAdapter() }

    override fun initView() {
        binding.viewModel = viewModel
        viewModel.viewController = this
        binding.rcvOrderSupplier.adapter = adapter
        activity?.intent?.getStringExtra(Constants.Key.INVOICE_ID)?.let {
            viewModel.getInvoice(it)
        }
        setupOnClickListener()
        binding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupOnClickListener() {
        binding.apply {
            lnPaymentMethod.setOnClickListener {
                showDialogChoosePaymentType()
            }
            constrainVoucherPayment.setOnClickListener {
                navigateForResult(PaymentFragmentDirections.actionPaymentFragmentToPromotionFragment(), Constants.Key.PROMOTION, object : ResultCallback {
                    override fun onResult(key: String, data: Bundle?) {
                        data?.let {
                            it.getSerializable(Constants.Key.PROMOTION)?.let { _promotion ->
                                val promotion = _promotion as Promotion
                                this@PaymentFragment.viewModel.applyPromotion(promotion)
                            }
                        }
                    }
                })
            }
        }
    }

    override fun observer() {
        viewModel.order.observe(viewLifecycleOwner) {
            setupOrder(order = it)
        }

        viewModel.promotion.observe(viewLifecycleOwner) {
            it?.let {
                binding.txtDiscount.text = "-${Utils.formatVnCurrency(it.value)}"
                if (it.name.isNotEmpty()) {
                    binding.voucherPayment.text = it.name
                }
            }
        }

        viewModel.finalPrice.observe(viewLifecycleOwner) { finalPrice ->
            binding.apply {
                finalSumProductPayment.text = Utils.formatVnCurrency(finalPrice)
                txtSum.text = Utils.formatVnCurrency(finalPrice)
            }
        }
    }

    private fun setupOrder(order: Order) {
        adapter.submitList(order.orderSuppliers)
        binding.apply {
            sumProductPayment.text = Utils.formatVnCurrency(order.totalPrice)
            sumShipperPayment.text = Utils.formatVnCurrency(order.totalShipmentPrice)
        }
    }

    override fun onStateError() {
        super.onStateError()
        viewModel.error.run {
            val message = getValueIfNotHandled()
            if (!message.isNullOrEmpty()) {
                DialogUtils.showErrorDialog(
                    context = requireContext(),
                    message = message
                )
            }
        }
    }

    private fun showDialogChoosePaymentType() {
        val dialog = Dialog(requireContext())
        val dialogBinding: DialogChoosePaymentMethodBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_choose_payment_method,
            binding.root as ViewGroup,
            false
        )
        dialog.setContentView(dialogBinding.root)
        dialogBinding.lifecycleOwner = this
        dialogBinding.viewModel = viewModel
        dialogBinding.txtAgree.setOnClickListener {
            dialog.hide()
        }
        dialog.setCancelable(false)
        dialog.showWithTransparentBackground()
    }

    override fun openVnPaySdk() {
        lifecycleScope.launch {
            val vnPayUrl = viewModel.getVnPayPaymentUrl().await()
            vnPayUrl?.let {
                val intentVnPaySdk = getIntentVnPay(it)
                setupSdkCompletedCallback()
                startActivity(intentVnPaySdk)
            }
        }
    }

    override fun payWithCODSuccess() {
        // navigate to delivery
        lifecycleScope.launch {
            delay(500)
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra(Constants.Key.SCREEN, Constants.Screen.DISCOVERY)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun getIntentVnPay(vnPayUrl: String): Intent {
        return Intent(requireActivity(), VNP_AuthenticationActivity::class.java).apply {
            putExtra(Constants.Payment.KEY_URL, vnPayUrl)
            putExtra(Constants.Payment.KEY_TMN_CODE, Constants.Payment.TMN_CODE)
            putExtra(Constants.Payment.KEY_SCHEME, Constants.Payment.SCHEME)
            putExtra(Constants.Payment.KEY_IS_SANDBOX, Constants.Payment.IS_SANDBOX)
        }
    }

    private fun setupSdkCompletedCallback() {
        VNP_AuthenticationActivity.setSdkCompletedCallback {
            Log.d("Sudoo", "VNPaySdkCompletedCallback: $it")
            when (it) {
                Constants.Payment.ACTION_APP_BACK -> {
                    onVnPaySdkActionAppBack()
                }
                Constants.Payment.ACTION_CALL_MOBILE_BANKING_APP -> {
                    onVnPaySdkActionCallMobileBankingApp()
                }
                Constants.Payment.ACTION_WEB_BACK -> {
                    onVnPaySdkActionWebBack()
                }
                Constants.Payment.ACTION_FAILED -> {
                    onVnPaySdkActionFailed()
                }
                Constants.Payment.ACTION_SUCCESS -> {
                    onVnPaySdkActionSuccess()
                }
                else -> {
                    onVnPaySdkActionFailed()
                }
            }
        }
    }

    private fun onVnPaySdkActionAppBack() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra(Constants.Key.SCREEN, Constants.Screen.DISCOVERY)
        startActivity(intent)
        activity?.finish()
    }

    private fun onVnPaySdkActionCallMobileBankingApp() {

    }

    private fun onVnPaySdkActionWebBack() {

    }

    private fun onVnPaySdkActionFailed() {
//        finish()
    }

    private fun onVnPaySdkActionSuccess() {
        activity?.finish()
    }

}