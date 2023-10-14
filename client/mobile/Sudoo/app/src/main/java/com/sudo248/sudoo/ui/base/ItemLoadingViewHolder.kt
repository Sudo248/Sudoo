package com.sudo248.sudoo.ui.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class ItemLoadingViewHolder<in T : Any>(
    view: View,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL
) : BasePageViewHolder<T, ViewBinding>(view = view) {

    init {
        itemView.layoutParams = ViewGroup.LayoutParams(
            if (orientation == RecyclerView.VERTICAL) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT,
            if (orientation == RecyclerView.VERTICAL) ViewGroup.LayoutParams.WRAP_CONTENT else ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onBind(item: T) {}
}