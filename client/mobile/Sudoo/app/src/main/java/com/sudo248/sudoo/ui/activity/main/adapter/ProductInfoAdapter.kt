package com.sudo248.sudoo.ui.activity.main.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sudo248.base_android.ktx.invisible
import com.sudo248.base_android.ktx.visible
import com.sudo248.sudoo.databinding.ItemProductBinding
import com.sudo248.sudoo.domain.entity.discovery.ProductInfo
import com.sudo248.sudoo.ui.base.BasePageListAdapter
import com.sudo248.sudoo.ui.base.BasePageViewHolder
import com.sudo248.sudoo.ui.base.LoadMoreListener
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import com.sudo248.sudoo.ui.util.Utils


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:37 - 12/03/2023
 */
class ProductInfoAdapter(
    private val onItemClick: (ProductInfo) -> Unit,
    onLoadMore: LoadMoreListener? = null,
) : BasePageListAdapter<ProductInfo, ProductInfoAdapter.ProductInfoViewHolder>() {
    override val enableLoadMore: Boolean = true

    init {
        onLoadMore?.let { setLoadMoreListener(it) }
    }
    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BasePageViewHolder<ProductInfo, *> {
        return ProductInfoViewHolder(ItemProductBinding.inflate(layoutInflater))
    }

    inner class ProductInfoViewHolder(binding: ItemProductBinding) : BasePageViewHolder<ProductInfo, ItemProductBinding>(binding) {
        override fun onBind(item: ProductInfo) {
            binding.apply {
                loadImage(imgProduct, item.images.first())
                nameProduct.text = item.name
                txtPrice.text = Utils.formatVnCurrency(item.price)
                if (item.price < item.listedPrice) {
                    txtListedPrice.visible()
                    txtListedPrice.text = Utils.formatVnCurrency(item.listedPrice)
                    txtListedPrice.paintFlags = txtListedPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    txtListedPrice.invisible()
                }

                if (item.discount > 0) {
                    txtDiscountPercent.visible()
                    txtDiscountPercent.text = Utils.formatDiscountPercent(item.discount)
                } else {
                    txtDiscountPercent.invisible()
                }
            }
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}