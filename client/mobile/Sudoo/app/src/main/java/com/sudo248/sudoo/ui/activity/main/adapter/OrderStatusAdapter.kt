package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sudo248.base_android.base.BaseListAdapter
import com.sudo248.base_android.base.BaseViewHolder
import com.sudo248.sudoo.data.dto.order.OrderSupplierUserInfoDto
import com.sudo248.sudoo.databinding.ItemOrderStatusBinding
import com.sudo248.sudoo.domain.entity.order.Order
import com.sudo248.sudoo.domain.entity.order.OrderCartProduct

class OrderStatusAdapter(
    private val onItemClick: (() -> Unit)? = null,
) : BaseListAdapter<OrderCartProduct, OrderStatusAdapter.OrderStatusViewHolder>() {

    inner class OrderStatusViewHolder(binding: ItemOrderStatusBinding) :
        BaseViewHolder<OrderCartProduct, ItemOrderStatusBinding>(binding) {
        override fun onBind(item: OrderCartProduct) {
            binding.apply {
                this.tvName.text = item.product.name
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