package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sudo248.base_android.base.BaseListAdapter
import com.sudo248.base_android.base.BaseViewHolder
import com.sudo248.sudoo.data.dto.order.OrderSupplierUserInfoDto
import com.sudo248.sudoo.databinding.ItemOrderStatusBinding

class OrderStatusAdapter(
    private val orderSuppliers: List<OrderSupplierUserInfoDto>,
    private val onItemClick: (() -> Unit)? = null,
) : BaseListAdapter<OrderSupplierUserInfoDto, OrderStatusAdapter.OrderStatusViewHolder>() {

    inner class OrderStatusViewHolder(binding: ItemOrderStatusBinding) :
        BaseViewHolder<OrderSupplierUserInfoDto, ItemOrderStatusBinding>(binding) {
        override fun onBind(item: OrderSupplierUserInfoDto) {
            binding.apply {


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

    override fun getItemCount(): Int = orderSuppliers.size

    override fun onBindViewHolder(holder: OrderStatusViewHolder, position: Int) {
        holder.onBind(orderSuppliers[position])
    }
}