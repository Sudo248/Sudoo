package com.sudo248.sudoo.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewbinding.ViewBinding
import com.sudo248.sudoo.R
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BasePageListAdapter<T : Any, VH : BasePageViewHolder<T, *>> :
    ListAdapter<T, BasePageViewHolder<T, *>>(
        BasePageDiffCallback<T>()
    ) {

    companion object {
        const val TYPE_VIEW_LOADING = -1
    }

    open val enableLoadMore: Boolean = false

    @LayoutRes
    protected open val layoutLoading: Int = R.layout.item_loading

    private var defaultVHClass: Class<BasePageViewHolder<T, *>>? = null

    private var inflaterViewBinding: Method? = null

    private var loadMoreListener: LoadMoreListener? = null

    private var loadMoreRecyclerViewScrollListener: LoadMoreRecyclerViewScrollListener? = null

    protected val thresholdInvisibleItem: Int? = null

    private var isFirstPage = true
    private var isLastPage = false

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (enableLoadMore && loadMoreListener != null && loadMoreRecyclerViewScrollListener == null) {
            loadMoreRecyclerViewScrollListener = object : LoadMoreRecyclerViewScrollListener(
                recyclerView.layoutManager!!, thresholdInvisibleItem ?: 0
            ) {
                override fun onLoadMore() {
                    loadMoreListener?.onLoadMore()
                }
            }
            recyclerView.addOnScrollListener(loadMoreRecyclerViewScrollListener!!)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        loadMoreRecyclerViewScrollListener?.let { recyclerView.removeOnScrollListener(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasePageViewHolder<T, *> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_VIEW_LOADING) {
            ItemLoadingViewHolder(
                layoutInflater.inflate(layoutLoading, parent, false),
                getRecyclerViewOrientation(parent as RecyclerView)
            )
        } else {
            getViewHolder(layoutInflater, parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: BasePageViewHolder<T, *>, position: Int) {
        if (holder.itemViewType != TYPE_VIEW_LOADING) {
            holder.onBind(getItem(position))
        } else {
            holder.itemView.isGone = isLastPage || currentList.isEmpty()
        }
    }

    open fun submitData(list: List<T>?, extend: Boolean = false) {
        if (isFirstPage) isFirstPage = !list.isNullOrEmpty()
        if (extend) {
            val newList = currentList.toMutableList()
            newList.addAll(list ?: emptyList())
            super.submitList(newList)
        } else {
            super.submitList(list?.toList())
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (enableLoadMore && isLastPosition(position)) {
            TYPE_VIEW_LOADING
        } else {
            getViewType(position)
        }
    }

    override fun getItemCount(): Int =
        if (enableLoadMore && !isFirstPage) currentList.size + 1 else currentList.size

    private fun isLastPosition(position: Int): Boolean = position >= currentList.size

    private fun getMethodInflateViewBinding(vhClass: Class<BasePageViewHolder<T, *>>): Method {
        val viewBindingClass =
            (vhClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<ViewBinding>
        return viewBindingClass.getMethod(
            "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
        )
    }

    private fun getDefaultViewBinding(
        vhClass: Class<BasePageViewHolder<T, *>>,
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ): ViewBinding {
        val inflater = inflaterViewBinding ?: getMethodInflateViewBinding(vhClass).also {
            inflaterViewBinding = it
        }
        return inflater.invoke(null, layoutInflater, parent, attachToParent) as ViewBinding
    }

    private fun getDefaultViewHolder(): Class<BasePageViewHolder<T, *>> {
        return defaultVHClass
            ?: ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<BasePageViewHolder<T, *>>).also {
                defaultVHClass = it
            }
    }

    fun getRecyclerViewOrientation(recyclerView: RecyclerView): Int {
        return recyclerView.layoutManager.let {
            when (it) {
                is LinearLayoutManager -> {
                    it.orientation
                }

                is StaggeredGridLayoutManager -> {
                    it.orientation
                }

                else -> {
                    RecyclerView.VERTICAL
                }
            }
        }
    }

    fun setLoadMoreListener(loadMoreListener: LoadMoreListener) {
        this.loadMoreListener = loadMoreListener
    }

    fun isLastPage(isLastPage: Boolean) {
        this.isLastPage = isLastPage
        loadMoreRecyclerViewScrollListener?.isLastPage = isLastPage
    }

    open fun getViewType(position: Int): Int = 0

    open fun getViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup, viewType: Int
    ): BasePageViewHolder<T, *> {
        val vhClass = getDefaultViewHolder()
        val defaultItemBinding = getDefaultViewBinding(vhClass, layoutInflater, parent, false)
        return vhClass.constructors[0].newInstance(defaultItemBinding) as BasePageViewHolder<T, *>
    }
}