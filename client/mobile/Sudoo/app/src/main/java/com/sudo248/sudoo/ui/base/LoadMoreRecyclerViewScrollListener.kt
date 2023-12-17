package com.sudo248.sudoo.ui.base

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class LoadMoreRecyclerViewScrollListener(
    private val layoutManager: RecyclerView.LayoutManager,
    private var thresholdInvisibleItem: Int = 0
) : RecyclerView.OnScrollListener(), LoadMoreListener {

    init {
        thresholdInvisibleItem = when (layoutManager) {
            is GridLayoutManager -> thresholdInvisibleItem * layoutManager.spanCount
            is StaggeredGridLayoutManager -> thresholdInvisibleItem * layoutManager.spanCount
            else -> thresholdInvisibleItem
        }
    }

    private var currentPage = 0

    private var previousTotalItemCount = 0

    private var isLoading = true

    var isLastPage = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        // minus 1 because last item is loading
        val currentTotalItemCount = layoutManager.itemCount - 1

        if (currentTotalItemCount < previousTotalItemCount) {
            currentPage = 0
            previousTotalItemCount = currentTotalItemCount
            if (currentTotalItemCount == 0) isLoading = true
        }

        if (isLoading && currentTotalItemCount > previousTotalItemCount) {
            isLoading = false
            previousTotalItemCount = currentTotalItemCount
        }

        if (!isLastPage && !isLoading && getLastVisibleItemPosition() + thresholdInvisibleItem >= currentTotalItemCount) {
            currentPage++
            isLoading = true
            onLoadMore()
        }
    }

    private fun getLastVisibleItemPosition(): Int {
        return when (layoutManager) {
            is GridLayoutManager -> {
                layoutManager.findLastVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                layoutManager.findLastVisibleItemPosition()
            }
            is StaggeredGridLayoutManager -> {
                layoutManager.findLastVisibleItemPositions(null).max()
            }
            else -> {
                0
            }
        }
    }

    fun reset() {
        currentPage = 0
        previousTotalItemCount = 0
        isLoading = true
    }
}