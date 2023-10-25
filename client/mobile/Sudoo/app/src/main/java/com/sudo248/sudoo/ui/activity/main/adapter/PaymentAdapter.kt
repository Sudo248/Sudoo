package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sudo248.base_android.base.BaseListAdapter
import com.sudo248.base_android.base.BaseViewHolder
import com.sudo248.sudoo.databinding.ItemPaymentBinding
import com.sudo248.sudoo.domain.entity.cart.CartProduct

class PaymentAdapter : BaseListAdapter<CartProduct, PaymentAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemPaymentBinding) :
        BaseViewHolder<CartProduct, ItemPaymentBinding>(binding) {
        override fun onBind(item: CartProduct) {
//            binding.apply {
//                loadImage(imageProductPayment, item.supplierProduct.product.images[0])
//                txtNameProduct.text = item.supplierProduct.product.name
//                txtCountProduct.text = "X${item.amount}"
//                txtPricesProduct.text = Utils.formatVnCurrency(item.supplierProduct.price)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}