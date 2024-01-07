package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ItemNotYetReviewBinding
import com.sudo248.sudoo.databinding.ItemReviewedBinding
import com.sudo248.sudoo.domain.entity.discovery.ProductInfo
import com.sudo248.sudoo.domain.entity.discovery.Review
import com.sudo248.sudoo.ui.base.BasePageListAdapter
import com.sudo248.sudoo.ui.base.BasePageViewHolder
import com.sudo248.sudoo.ui.base.LoadMoreListener
import com.sudo248.sudoo.ui.models.review.ReviewListTab
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import com.sudo248.sudoo.ui.util.Utils

class ReviewAdapter(
    private val tab: ReviewListTab,
    onLoadMore: LoadMoreListener? = null,
    private val onClickProduct: ((ProductInfo) -> Unit)? = null,
    private val onClickReview: ((Review) -> Unit)? = null
) : BasePageListAdapter<Review, BasePageViewHolder<Review, *>>() {
    override val enableLoadMore: Boolean = true

    init {
        onLoadMore?.let { setLoadMoreListener(it) }
    }

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BasePageViewHolder<Review, *> {
        return if (tab == ReviewListTab.REVIEWED) {
            ReviewedViewHolder(ItemReviewedBinding.inflate(layoutInflater, parent, false))
        } else {
            NotYetReviewViewHolder(ItemNotYetReviewBinding.inflate(layoutInflater, parent, false))
        }
    }

    inner class NotYetReviewViewHolder(binding: ItemNotYetReviewBinding) :
        BasePageViewHolder<Review, ItemNotYetReviewBinding>(binding) {
        override fun onBind(item: Review) {
            binding.apply {
                item.productInfo?.let {
                    loadImage(imgProduct, item.productInfo.images.first())
                    txtNameProduct.text = item.productInfo.name
                    txtBrand.text =
                        itemView.context.getString(R.string.product_brand, item.productInfo.brand)
                    txtPrice.text = Utils.formatVnCurrency(item.productInfo.price)
                }
                txtReview.setOnClickListener {
                    onClickReview?.invoke(item)
                }
            }
        }

    }

    inner class ReviewedViewHolder(binding: ItemReviewedBinding) :
        BasePageViewHolder<Review, ItemReviewedBinding>(binding) {
        override fun onBind(item: Review) {
            binding.apply {
                loadImage(imgAvatar, item.userInfo.avatar)
                txtNameUser.text = item.userInfo.fullName
                rating.rating = item.rate
                txtCommentTime.text = Utils.formatDateTime(item.updatedAt)
                txtComment.text = item.comment
                item.productInfo?.let {
                    loadImage(imgProduct, item.productInfo.images.first())
                    txtNameProduct.text = item.productInfo.name
                }
            }
        }
    }

    val isEmpty
        get() = currentList.isEmpty()

}