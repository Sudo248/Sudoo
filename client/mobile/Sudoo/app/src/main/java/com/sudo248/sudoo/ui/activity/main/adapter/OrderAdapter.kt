package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sudo248.base_android.base.BaseListAdapter
import com.sudo248.base_android.base.BaseViewHolder
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ItemOrderBinding
import com.sudo248.sudoo.domain.entity.order.OrderSupplier
import com.sudo248.sudoo.ui.util.Utils

class OrderAdapter : BaseListAdapter<OrderSupplier, OrderAdapter.OrderSupplierViewHolder>() {

    inner class OrderSupplierViewHolder(binding: ItemOrderBinding) :
        BaseViewHolder<OrderSupplier, ItemOrderBinding>(binding) {
        override fun onBind(item: OrderSupplier) {
            binding.apply {
                txtNameSupplier.text = item.supplier.name
                shipment.apply {
                    txtShipmentType.text = item.shipment.shipmentType.value
                    txtShipmentPrice.text = Utils.formatVnCurrency(item.shipment.shipmentPrice)
                    txtReceivedDate.text = itemView.context.getString(
                        R.string.received_date,
                        Utils.formatReceivedDate(System.currentTimeMillis() + item.shipment.deliveryTime)
                    )
                }
                totalPrice.apply {
                    txtSumProduct.text = itemView.context.getString(
                        R.string.total_money_format,
                        "${item.orderCartProducts.size}"
                    )
                    txtSumPrice.text = Utils.formatVnCurrency(item.totalPrice)
                }
                rcvOrderProducts.adapter = OrderProductAdapter(item.orderCartProducts)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderSupplierViewHolder {
        return OrderSupplierViewHolder(
            ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}