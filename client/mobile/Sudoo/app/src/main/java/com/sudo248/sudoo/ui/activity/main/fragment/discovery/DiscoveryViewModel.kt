package com.sudo248.sudoo.ui.activity.main.fragment.discovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.domain.entity.discovery.Category
import com.sudo248.sudoo.domain.entity.discovery.Offset
import com.sudo248.sudoo.domain.entity.discovery.ProductInfo
import com.sudo248.sudoo.domain.repository.DiscoveryRepository
import com.sudo248.sudoo.ui.activity.main.adapter.CategoryAdapter
import com.sudo248.sudoo.ui.activity.main.adapter.ProductInfoAdapter
import com.sudo248.sudoo.ui.base.EndlessNestedScrollListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:34 - 12/03/2023
 */
@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val discoveryRepository: DiscoveryRepository
) : BaseViewModel<NavDirections>() {
    private var nextOffset: Offset = Offset()

    private val _isRefresh = MutableLiveData(false)
    val isRefresh: LiveData<Boolean> = _isRefresh

    var error: SingleEvent<String?> = SingleEvent(null)

    val categoryAdapter = CategoryAdapter(::onCategoryItemClick)

    val productInfoAdapter = ProductInfoAdapter(::onProductItemClick)

    val endlessNestedScrollListener = object : EndlessNestedScrollListener() {
        override fun onLoadMore() {
            loadMoreProduct()
        }
    }

    init {
        refresh()
    }

    fun refresh() {
        _isRefresh.value = true
        getCategories()
        getRecommendProductList()
    }

    private fun getCategories() = launch {
        discoveryRepository.getCategories()
            .onSuccess { categories ->
                categoryAdapter.submitList(categories)
                _isRefresh.value = false
            }
            .onError {
                error = SingleEvent(it.message)
                _isRefresh.value = false
            }
    }


    private fun getRecommendProductList(isLoadMore: Boolean = false) = launch {
        if (!isLoadMore) {
            nextOffset.reset()
        }
        discoveryRepository.getRecommendProductList(nextOffset)
            .onSuccess {
                if (it.products.size < nextOffset.limit) {
                    productInfoAdapter.isLastPage(true)
                    endlessNestedScrollListener.isLastPage(true)
                } else {
                    nextOffset.offset = it.pagination.offset
                }
                productInfoAdapter.submitData(it.products, extend = isLoadMore)
            }
            .onError {
                error = SingleEvent(it.message)
            }
    }

    private fun onCategoryItemClick(item: Category) {
        navigator.navigateTo(
            DiscoveryFragmentDirections.actionDiscoveryFragmentToSearchFragment(
                categoryId = item.categoryId,
                categoryName = item.name
            )
        )
    }

    private fun onProductItemClick(item: ProductInfo) {
        navigateToProductDetail(item.productId)
    }

    fun loadMoreProduct() {
        getRecommendProductList(isLoadMore = true)
    }

    fun navigateToSearchView() {
        navigator.navigateTo(
            DiscoveryFragmentDirections.actionDiscoveryFragmentToSearchFragment()
        )
    }

    fun navigateToProductDetail(productId: String) {
        navigator.navigateTo(
            DiscoveryFragmentDirections.actionDiscoveryFragmentToProductDetailFragment(
                productId
            )
        )
    }
}