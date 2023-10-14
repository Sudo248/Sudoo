package com.sudo248.sudoo.ui.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.sudo248.base_android.base.ItemDiff

class BasePageDiffCallback <T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is ItemDiff) {
            oldItem.isItemTheSame(newItem as ItemDiff)
        } else {
            oldItem == newItem
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is ItemDiff) {
            oldItem.isContentTheSame(newItem as ItemDiff)
        } else {
            oldItem == newItem
        }
    }
}
