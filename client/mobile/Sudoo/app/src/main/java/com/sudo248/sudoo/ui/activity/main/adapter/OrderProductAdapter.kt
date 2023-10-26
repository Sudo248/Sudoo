package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sudo248.sudoo.databinding.ItemOrderProductBinding
import com.sudo248.sudoo.domain.entity.order.OrderCartProduct
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import com.sudo248.sudoo.ui.util.Utils

class OrderProductAdapter(
    private val orderProducts: List<OrderCartProduct>,
    private val onItemClick: ((OrderCartProduct) -> Unit)? = null
) : RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder>() {
    inner class OrderProductViewHolder(val binding: ItemOrderProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(orderProduct: OrderCartProduct) {
            binding.apply {
                loadImage(imageProductPayment, orderProduct.product.images[0])
                txtNameProduct.text = orderProduct.product.name
                txtCountProduct.text = "X${orderProduct.quantity}"
                txtPricesProduct.text = Utils.formatVnCurrency(orderProduct.product.price)
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(orderProduct)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductViewHolder {
        return OrderProductViewHolder(
            ItemOrderProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = orderProducts.size

    override fun onBindViewHolder(holder: OrderProductViewHolder, position: Int) {
        holder.onBind(orderProducts[position])
    }
}