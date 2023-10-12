package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.sudo248.base_android.base.BaseListAdapter
import com.sudo248.base_android.base.BaseViewHolder
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ItemCategoryBinding
import com.sudo248.sudoo.ui.uimodel.CategoryUiModel


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:37 - 12/03/2023
 */
class CategoryAdapter(
    private val onItemClick: (CategoryUiModel) -> Unit,
) : BaseListAdapter<CategoryUiModel, CategoryAdapter.ViewHolder>() {
    private lateinit var lastSelectedItemBinding: ItemCategoryBinding
    private var currentSelectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_category,
                parent,
                false
            )
        )
    }

    inner class ViewHolder(binding: ItemCategoryBinding) :
        BaseViewHolder<CategoryUiModel, ItemCategoryBinding>(binding) {

        override fun onBind(item: CategoryUiModel) {
            binding.category = item
            if (bindingAdapterPosition == currentSelectedPosition) {
                lastSelectedItemBinding = binding
                selectedItem(binding)
            }
            itemView.setOnClickListener {
                if (!it.isSelected) {
                    setSelectedItem()
                    onItemClick.invoke(item)
                }
                currentSelectedPosition = bindingAdapterPosition
            }
        }

        private fun setSelectedItem() {
            notSelectedItem(lastSelectedItemBinding)
            selectedItem(binding)
            lastSelectedItemBinding = binding
        }

        private fun selectedItem(itemBinding: ItemCategoryBinding) {
            itemBinding.apply {
                root.isSelected = true
                cardContainer.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.primaryColor
                    )
                )
                txtNameCategory.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
            }
        }

        private fun notSelectedItem(itemBinding: ItemCategoryBinding) {
            itemBinding.apply {
                root.isSelected = false
                cardContainer.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
                txtNameCategory.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.gray_88
                    )
                )
            }
        }
    }
}