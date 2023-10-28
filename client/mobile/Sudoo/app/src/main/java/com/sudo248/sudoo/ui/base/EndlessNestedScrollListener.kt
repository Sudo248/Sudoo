package com.sudo248.sudoo.ui.base

import androidx.core.widget.NestedScrollView

abstract class EndlessNestedScrollListener : NestedScrollView.OnScrollChangeListener,
    LoadMoreListener {
    private var isLastPage = false

    override fun onScrollChange(
        v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int
    ) {
        if (!isLastPage && v.getChildAt(v.childCount - 1) != null) {
            if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) && scrollY > oldScrollY) {
                onLoadMore()
            }
        }
    }

    fun isLastPage(isLastPage: Boolean) {
        this.isLastPage = isLastPage
    }
}