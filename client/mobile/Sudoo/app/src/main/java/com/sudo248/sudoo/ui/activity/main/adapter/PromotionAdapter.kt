package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sudo248.base_android.base.BaseListAdapter
import com.sudo248.base_android.base.BaseViewHolder
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ItemCategoryBinding
import com.sudo248.sudoo.databinding.ItemPromotionBinding
import com.sudo248.sudoo.domain.entity.promotion.Promotion
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import com.sudo248.sudoo.ui.util.Utils

class PromotionAdapter(
    private val onItemSelected: (Promotion) -> Unit
) : BaseListAdapter<Promotion, PromotionAdapter.ViewHolder>() {

    private lateinit var lastSelectedItemBinding: ItemPromotionBinding
    private var currentSelectedPosition = 0

    inner class ViewHolder(binding: ItemPromotionBinding) :
        BaseViewHolder<Promotion, ItemPromotionBinding>(binding) {
        override fun onBind(item: Promotion) {
            binding.apply {
                loadImage(binding.imageVoucher, item.image)
                txtNamePromotion.text = item.name
                txtReducePrice.text = Utils.formatVnCurrency(item.value)
                txtRemain.text = itemView.context.getString(R.string.remain, "${item.totalAmount}")
            }
            itemView.setOnClickListener {
                if (!it.isSelected) {
                    setSelectedItem()
                    onItemSelected.invoke(item)
                }
                currentSelectedPosition = adapterPosition
            }
        }

        private fun setSelectedItem() {
            if (this@PromotionAdapter::lastSelectedItemBinding.isInitialized) {
                notSelectedItem(lastSelectedItemBinding)
            }
            selectedItem(binding)
            lastSelectedItemBinding = binding
        }

        private fun selectedItem(itemBinding: ItemPromotionBinding) {
            itemBinding.apply {
                root.isSelected = true
                root.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.primaryColor
                    )
                )
                val colorWhite = ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
                txtNamePromotion.setTextColor(colorWhite)
                txtRemain.setTextColor(colorWhite)
                txtReducePrice.setTextColor(colorWhite)
            }
        }

        private fun notSelectedItem(itemBinding: ItemPromotionBinding) {
            itemBinding.apply {
                root.isSelected = false
                root.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
                val colorBlack = ContextCompat.getColor(
                    itemView.context,
                    R.color.black
                )
                txtNamePromotion.setTextColor(colorBlack)
                txtRemain.setTextColor(colorBlack)
                txtReducePrice.setTextColor(colorBlack)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPromotionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}