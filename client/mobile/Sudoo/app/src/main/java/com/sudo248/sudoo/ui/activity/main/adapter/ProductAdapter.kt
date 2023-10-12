package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sudo248.base_android.base.BaseListAdapter
import com.sudo248.base_android.base.BaseViewHolder
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ItemProductBinding
import com.sudo248.sudoo.ui.uimodel.CategoryUiModel
import com.sudo248.sudoo.ui.uimodel.ProductUiModel


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:37 - 12/03/2023
 */
class ProductAdapter(
    private val onItemClick: (ProductUiModel) -> Unit
) : BaseListAdapter<ProductUiModel, ProductAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ItemProductBinding) : BaseViewHolder<ProductUiModel, ItemProductBinding>(binding) {
        override fun onBind(item: ProductUiModel) {
            binding.product = item
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product,
                parent,
                false
            )
        )
    }
}