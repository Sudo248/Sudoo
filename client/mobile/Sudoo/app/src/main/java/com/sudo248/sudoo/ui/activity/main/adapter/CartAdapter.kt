package com.sudo248.sudoo.ui.activity.main.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.apachat.swipereveallayout.core.ViewBinder
import com.sudo248.base_android.base.BaseListAdapter
import com.sudo248.base_android.base.BaseViewHolder
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.invisible
import com.sudo248.base_android.ktx.visible
import com.sudo248.sudoo.databinding.ItemCartBinding
import com.sudo248.sudoo.domain.entity.cart.AddCartProduct
import com.sudo248.sudoo.domain.entity.cart.CartProduct
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import com.sudo248.sudoo.ui.util.Utils

class CartAdapter(
    private val onUpdateItemAmount: (AddCartProduct) -> Unit,
    private val onDeleteItem: (AddCartProduct) -> Unit,
    private val onCheckedChangeItem: (Boolean, CartProduct) -> Unit,
) : BaseListAdapter<CartProduct, CartAdapter.ViewHolder>() {

    private val viewBinder: ViewBinder = ViewBinder()

    inner class ViewHolder(binding: ItemCartBinding) :
        BaseViewHolder<CartProduct, ItemCartBinding>(binding) {
        override fun onBind(item: CartProduct) {
            viewBinder.bind(
                binding.swipeRevealLayout,
                "${item.product?.productId}"
            )
            binding.apply {
                loadImage(imgItem, item.product?.images?.get(0) ?: "")
                txtNameItem.text = item.product?.name
                txtCountItem.text = item.quantity.toString()
                txtPriceItem.text = Utils.formatVnCurrency(item.product?.price?.times(1.0) ?: 0.0)
                if (item.product != null && item.product.price < item.product.listedPrice) {
                    txtListedPriceItem.visible()
                    txtListedPriceItem.text = Utils.formatVnCurrency(item.product.listedPrice.times(1.0))
                    txtListedPriceItem.paintFlags = txtListedPriceItem.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    txtListedPriceItem.gone()
                }

                minusOrder.setOnClickListener {
                    if (item.quantity > 1) {
                        item.quantity -= 1
                        onUpdateItemAmount.invoke(
                            AddCartProduct(
                                productId = item.product!!.productId,
                                amount = -1
                            )
                        )
                        txtCountItem.text = item.quantity.toString()
                    } else {
                        onDeleteItem.invoke(
                            AddCartProduct(
                                productId = item.product!!.productId,
                                amount = -item.quantity
                            )
                        )
                        txtCountItem.text = item.quantity.toString()
                    }
                }

                addOrder.setOnClickListener {
                    item.quantity += 1
                    onUpdateItemAmount.invoke(
                        AddCartProduct(
                            productId = item.product?.productId ?: "",
                            amount = 1
                        )
                    )
                    txtCountItem.text = item.quantity.toString()
                }
                btnDeleteItem.setOnClickListener {
                    onDeleteItem.invoke(
                        AddCartProduct(
                            productId = item.product?.productId?:"",
                            amount = item.quantity
                        )
                    )
                }
                binding.checkboxItem.setOnCheckedChangeListener { _, isChecked ->
                    onCheckedChangeItem(isChecked, item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}
