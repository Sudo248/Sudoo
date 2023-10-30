package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.apachat.swipereveallayout.core.ViewBinder
import com.sudo248.base_android.base.BaseListAdapter
import com.sudo248.base_android.base.BaseViewHolder
import com.sudo248.sudoo.databinding.ItemCartBinding
import com.sudo248.sudoo.domain.entity.cart.AddCartProduct
import com.sudo248.sudoo.domain.entity.cart.CartProduct
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import com.sudo248.sudoo.ui.util.Utils

class CartAdapter(
    private val onUpdateItemAmount: (AddCartProduct) -> Unit,
    private val onDeleteItem: (AddCartProduct) -> Unit
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
//                    if (item.quantity >= item.supplierProduct.amountLeft) return@setOnClickListener
                    item.quantity += 1
                    onUpdateItemAmount.invoke(
                        AddCartProduct(
                            productId = item.product?.productId ?: "",
                            amount = 1
                        )
                    )
                    txtCountItem.text = item.quantity.toString()
                }
//
                btnDeleteItem.setOnClickListener {
                    onDeleteItem.invoke(
                        AddCartProduct(
                            productId = item.product?.productId?:"",
                            amount = item.quantity
                        )
                    )
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
