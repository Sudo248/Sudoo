package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sudo248.base_android.base.BaseListAdapter
import com.sudo248.base_android.base.BaseViewHolder
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.visible
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ItemOrderStatusBinding
import com.sudo248.sudoo.domain.entity.order.OrderStatus
import com.sudo248.sudoo.domain.entity.order.OrderSupplierInfo
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import com.sudo248.sudoo.ui.util.Utils

class OrderListAdapter(
    private val onItemClick: ((OrderSupplierInfo) -> Unit)? = null,
    private val onNextAction: ((OrderSupplierInfo) -> Unit)? = null
) : BaseListAdapter<OrderSupplierInfo, OrderListAdapter.OrderStatusViewHolder>() {

    inner class OrderStatusViewHolder(binding: ItemOrderStatusBinding) :
        BaseViewHolder<OrderSupplierInfo, ItemOrderStatusBinding>(binding) {
        override fun onBind(item: OrderSupplierInfo) {
            binding.apply {
                loadImage(imgProduct, item.orderCartProduct.product.images.first())
                txtSupplierName.text = item.supplierName
                txtStatus.text = itemView.context.getString(item.status.displayValue)
                txtNameProduct.text = item.orderCartProduct.product.name
                txtQuantity.text = "x${item.orderCartProduct.quantity}"
                txtPurchasePrice.text = Utils.formatVnCurrency(item.orderCartProduct.purchasePrice ?: item.orderCartProduct.product.price)
                txtTotalProduct.text = itemView.context.getString(R.string.total_product, item.totalProduct)
                txtTotalPrice.text = Utils.formatVnCurrency(item.totalPrice)
                when(item.status) {
                    OrderStatus.TAKE_ORDER, OrderStatus.SHIPPING, OrderStatus.DELIVERED -> {
                        groupNextAction.visible()
                        txtDescription.text = itemView.context.getString(R.string.received_order_description)
                        txtNextAction.text = itemView.context.getString(R.string.received_order)
                    }
                    OrderStatus.RECEIVED -> {
                        groupNextAction.visible()
                        txtDescription.text = itemView.context.getString(R.string.review_description)
                        txtNextAction.text = itemView.context.getString(R.string.review)
                    }
                    else -> {
                        groupNextAction.gone()
                    }
                }
                itemView.setOnClickListener {
                    onItemClick?.invoke(item)
                }
                txtNextAction.setOnClickListener {
                    onNextAction?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderStatusViewHolder {
        return OrderStatusViewHolder(
            ItemOrderStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

}