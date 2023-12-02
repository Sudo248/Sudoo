package com.sudoo.shipment.ui

import androidx.activity.viewModels
import com.sudo248.base_android.base.BaseActivity
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.visible
import com.sudo248.base_android.utils.DialogUtils
import com.sudoo.shipment.Constants
import com.sudoo.shipment.R
import com.sudoo.shipment.databinding.ActivityOrderDetailBinding
import com.sudoo.shipment.model.OrderStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailActivity : BaseActivity<ActivityOrderDetailBinding, OrderDetailViewModel>() {
    override val viewModel: OrderDetailViewModel by viewModels()
    override val enableStateScreen: Boolean = true
    override fun initView() {
        val orderSupplierId = intent.getStringExtra(Constants.ORDER_ID)
        viewModel.getOrderSupplierDetail(orderSupplierId)
    }

    override fun onStateError() {
        super.onStateError()
        viewModel.error.run {
            val message = getValueIfNotHandled()
            if (!message.isNullOrEmpty()) {
                DialogUtils.showErrorDialog(
                    context = this@OrderDetailActivity,
                    message = message
                )
            }
        }
    }

    override fun observer() {
        viewModel.order.observe(this) {
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
                    txtReceiveMoney.text = Utils.formatVnCurrency(
                        if (it.payment.paymentType.lowercase() == "cod")
                            it.finalPrice
                        else 0.0
                    )
                } else {
                    txtPaymentMethod.text = getString(R.string.have_not_yet_payment)
                    constrainPaymentTime.gone()
                    lnPaymentMethod.gone()
                }
                txtOrderId.text = "#${orderSupplier.orderSupplierId}"
                txtOrderStatus.text = getString(orderSupplier.status.displayValue)
                txtOrderTime.text = Utils.formatDateTime(it.createdAt)
                if (orderSupplier.shipment.receivedDateTime != null) {
                    constrainReceivedTime.visible()
                    txtReceivedTime.text =
                        Utils.formatDateTime(orderSupplier.shipment.receivedDateTime)
                } else {
                    constrainReceivedTime.gone()
                }
            }
        }
        viewModel.orderStatus.observe(this) {
            binding.apply {
                when (it) {
                    OrderStatus.READY -> {
                        txtAction.visible()
                        txtAction.text = getString(R.string.received_order)
                        txtAction.setOnClickListener {
                            viewModel.patchOrderStatus(OrderStatus.TAKE_ORDER)
                        }
                    }

                    OrderStatus.TAKE_ORDER -> {
                        txtAction.visible()
                        txtAction.text = getString(R.string.shipping_order)
                        txtAction.setOnClickListener {
                            viewModel.patchOrderStatus(OrderStatus.SHIPPING)
                        }
                    }

                    OrderStatus.SHIPPING -> {
                        txtAction.visible()
                        txtAction.text = getString(R.string.delivered_order)
                        txtAction.setOnClickListener {
                            viewModel.patchOrderStatus(OrderStatus.DELIVERED)
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