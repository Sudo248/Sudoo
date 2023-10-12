package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.apachat.swipereveallayout.core.ViewBinder
import com.sudo248.base_android.base.BaseListAdapter
import com.sudo248.base_android.base.BaseViewHolder
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ItemCartBinding
import com.sudo248.sudoo.databinding.ItemProductBinding
import com.sudo248.sudoo.domain.entity.cart.AddSupplierProduct
import com.sudo248.sudoo.domain.entity.cart.CartSupplierProduct
import com.sudo248.sudoo.ui.uimodel.ProductUiModel
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import com.sudo248.sudoo.ui.util.Utils

class CartAdapter(
    private val onUpdateItemAmount: (AddSupplierProduct) -> Unit,
    private val onDeleteItem: (AddSupplierProduct) -> Unit
) : BaseListAdapter<CartSupplierProduct, CartAdapter.ViewHolder>() {

    private val viewBinder: ViewBinder = ViewBinder()

    inner class ViewHolder(binding: ItemCartBinding) :
        BaseViewHolder<CartSupplierProduct, ItemCartBinding>(binding) {
        override fun onBind(item: CartSupplierProduct) {
            viewBinder.bind(
                binding.swipeRevealLayout,
                "${item.supplierProduct.supplier.supplierId}-${item.supplierProduct.product.productId}"
            )
            binding.apply {
                loadImage(imgItem, item.supplierProduct.product.images[0])
                txtNameItem.text = item.supplierProduct.product.name
                txtCountItem.text = item.amount.toString()
                txtPriceItem.text = Utils.formatVnCurrency(item.supplierProduct.price)

                minusOrder.setOnClickListener {
                    if (item.amount > 1) {
                        item.amount -= 1
                        onUpdateItemAmount.invoke(
                            AddSupplierProduct(
                                supplierId = item.supplierProduct.supplier.supplierId,
                                productId = item.supplierProduct.product.productId,
                                amount = item.amount
                            )
                        )
                        txtCountItem.text = item.amount.toString()
                    } else {
                        onDeleteItem.invoke(
                            AddSupplierProduct(
                                supplierId = item.supplierProduct.supplier.supplierId,
                                productId = item.supplierProduct.product.productId,
                                amount = item.amount
                            )
                        )
                    }

                }

                addOrder.setOnClickListener {
                    if (item.amount >= item.supplierProduct.amountLeft) return@setOnClickListener
                    item.amount += 1
                    onUpdateItemAmount.invoke(
                        AddSupplierProduct(
                            supplierId = item.supplierProduct.supplier.supplierId,
                            productId = item.supplierProduct.product.productId,
                            amount = item.amount
                        )
                    )
                    txtCountItem.text = item.amount.toString()
                }

                btnDeleteItem.setOnClickListener {
                    onDeleteItem.invoke(
                        AddSupplierProduct(
                            supplierId = item.supplierProduct.supplier.supplierId,
                            productId = item.supplierProduct.product.productId,
                            amount = item.amount
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