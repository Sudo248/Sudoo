package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sudo248.sudoo.databinding.ItemProductBinding
import com.sudo248.sudoo.domain.entity.discovery.ProductInfo
import com.sudo248.sudoo.ui.base.BasePageListAdapter
import com.sudo248.sudoo.ui.base.BasePageViewHolder
import com.sudo248.sudoo.ui.base.LoadMoreRecyclerViewListener
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
    onLoadMore: LoadMoreRecyclerViewListener
) : BasePageListAdapter<ProductInfo, ProductInfoAdapter.ProductInfoViewHolder>() {
    override val enableLoadMore: Boolean = true

    init {
        setLoadMoreListener(onLoadMore)
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
                loadImage(imageProduct, item.images.first())
                nameProduct.text = item.name
                priceProduct.text = Utils.formatVnCurrency(item.price)
                txtStarts.text = Utils.format(item.rate.toDouble(), digit = 1)
            }
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}