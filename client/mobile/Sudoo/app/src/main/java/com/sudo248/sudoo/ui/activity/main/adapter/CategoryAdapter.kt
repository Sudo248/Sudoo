package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sudo248.base_android.base.BaseListAdapter
import com.sudo248.base_android.base.BaseViewHolder
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ItemCategoryBinding
import com.sudo248.sudoo.domain.entity.discovery.Category
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:37 - 12/03/2023
 */
class CategoryAdapter(
    private val onItemClick: (Category) -> Unit,
) : BaseListAdapter<Category, CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(binding: ItemCategoryBinding) :
        BaseViewHolder<Category, ItemCategoryBinding>(binding) {

        override fun onBind(item: Category) {
            loadImage(binding.imgCategory, item.image)
            binding.txtNameCategory.text = item.name
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}