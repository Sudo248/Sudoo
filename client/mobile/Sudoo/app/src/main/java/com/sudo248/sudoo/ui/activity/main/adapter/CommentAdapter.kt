package com.sudo248.sudoo.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sudo248.base_android.base.BaseListAdapter
import com.sudo248.base_android.base.BaseViewHolder
import com.sudo248.sudoo.databinding.ItemCommentBinding
import com.sudo248.sudoo.domain.entity.discovery.Comment
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import com.sudo248.sudoo.ui.util.Utils

class CommentAdapter : BaseListAdapter<Comment, CommentAdapter.CommentViewHolder>() {
    inner class CommentViewHolder(binding: ItemCommentBinding) :
        BaseViewHolder<Comment, ItemCommentBinding>(binding) {
        override fun onBind(item: Comment) {
            binding.apply {
                loadImage(imgAvatar, item.userInfo.avatar)
                if (!item.images.isNullOrEmpty()) {
                    loadImage(imgComment, item.images.first())
                }

                txtNameUser.text = item.userInfo.fullName
                rating.rating = item.rate
                txtComment.text = item.comment
                txtCommentTime.text = Utils.formatDateTime(item.createAt)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    fun submitData(list: List<Comment>?, extend: Boolean = false) {
        if (extend) {
            val newList = currentList.toMutableList()
            newList.addAll(list ?: emptyList())
            super.submitList(newList)
        } else {
            super.submitList(list?.toList())
        }
    }


}