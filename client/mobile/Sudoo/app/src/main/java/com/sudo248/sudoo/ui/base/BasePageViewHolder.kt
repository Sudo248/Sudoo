package com.sudo248.sudoo.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BasePageViewHolder<in T : Any, VB : ViewBinding>(
    private val _binding: VB? = null,
    view: View? = null
) :
    RecyclerView.ViewHolder(
        _binding?.root ?: view ?: throw Exception("Item view or item binding is required")
    ) {
    protected val binding: VB get() = _binding!!

    abstract fun onBind(item: T)
}