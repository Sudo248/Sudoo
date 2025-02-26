package com.sudo248.sudoo.ui.activity.main.fragment.order_detail

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.visible
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.FragmentOrderDetailBinding
import com.sudo248.sudoo.domain.entity.order.OrderStatus
import com.sudo248.sudoo.ui.activity.main.adapter.OrderProductAdapter
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import com.sudo248.sudoo.ui.util.BundleKeys
import com.sudo248.sudoo.ui.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding, OrderDetailViewModel>() {
    override val enableStateScreen: Boolean = true
    override val viewModel: OrderDetailViewModel by viewModels()
    private val args: OrderDetailFragmentArgs by navArgs()

    override fun initView() {
        binding.viewModel = viewModel
        viewModel.getOrderSupplierDetail(args.orderSupplierId)
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

    @SuppressLint("SetTextI18n")
    override fun observer() {
        viewModel.order.observe(viewLifecycleOwner) {
            binding.apply {
                informationUser.text = "${it.user.fullName} | ${it.user.phone}\n${it.address}"
                val orderSupplier = it.orderSuppliers.first()
                txtSupplierName.text = orderSupplier.supplier.name
                rcvProducts.adapter = OrderProductAdapter(orderSupplier.orderCartProducts)
                txtTotalPrice.text = Utils.formatVnCurrency(orderSupplier.totalPrice)
                txtTotalShipmentPrice.text =
                    Utils.formatVnCurrency(orderSupplier.shipment.shipmentPrice)
                txtFinalPrice.text =
                    Utils.formatVnCurrency(it.finalPrice)
                if (it.payment != null) {
                    constrainPaymentTime.visible()
                    lnPaymentMethod.visible()
                    txtPaymentMethod.text = it.payment.paymentType
                    txtPaymentTime.text = Utils.formatDateTime(it.payment.paymentDateTime)
                } else {
                    txtPaymentMethod.text = getString(R.string.have_not_yet_payment)
                    constrainPaymentTime.gone()
                    lnPaymentMethod.gone()
                }
                txtOrderId.text = "#${orderSupplier.orderSupplierId}"
                txtOrderTime.text = Utils.formatDateTime(it.createdAt)
                if (orderSupplier.shipment.receivedDateTime != null) {
                    constrainReceivedTime.visible()
                    txtReceivedTime.text = Utils.formatDateTime(orderSupplier.shipment.receivedDateTime)
                } else {
                    constrainReceivedTime.gone()
                }
            }
        }
        viewModel.orderStatus.observe(viewLifecycleOwner) {
            binding.apply {
                when(it) {
                    OrderStatus.TAKE_ORDER,OrderStatus.SHIPPING,OrderStatus.DELIVERED -> {
                        txtAction.visible()
                        txtAction.text = getString(R.string.received_order)
                        txtAction.setOnClickListener {
                            this@OrderDetailFragment.viewModel.receivedOrder()
                        }
                    }
                    OrderStatus.RECEIVED -> {
                        txtAction.visible()
                        txtAction.text = getString(R.string.review)
                        txtAction.setOnClickListener {
                            navigateTo(OrderDetailFragmentDirections.actionOrderDetailFragmentToReviewListFragment())
                        }
                    }
                    else -> {
                        txtAction.gone()
                    }
                }
            }
        }
    }

}