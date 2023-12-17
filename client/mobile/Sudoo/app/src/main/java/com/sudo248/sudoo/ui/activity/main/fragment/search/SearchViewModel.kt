package com.sudo248.sudoo.ui.activity.main.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.domain.entity.discovery.Offset
import com.sudo248.sudoo.domain.entity.discovery.ProductInfo
import com.sudo248.sudoo.domain.repository.DiscoveryRepository
import com.sudo248.sudoo.ui.activity.main.adapter.ProductInfoAdapter
import com.sudo248.sudoo.ui.base.LoadMoreListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val discoveryRepository: DiscoveryRepository
) : BaseViewModel<NavDirections>() {
    private val _isEmptyProduct = MutableLiveData(false)
    val isEmptyProduct: LiveData<Boolean> = _isEmptyProduct

    private val _isRefresh = MutableLiveData(false)
    val isRefresh: LiveData<Boolean> = _isRefresh

    var error: SingleEvent<String?> = SingleEvent(null)

    val productInfoAdapter = ProductInfoAdapter(::onProductItemClick, object : LoadMoreListener {
        override fun onLoadMore() {
            loadMoreProduct()
        }
    })

    private var nextOffset: Offset = Offset()

    private var categoryId: String? = null

    private var currentSearchName: String = ""

    private var jobSearch: Job? = null

    fun search(name: String) {
        jobSearch?.cancel()
        jobSearch = launch {
            delay(200)
            currentSearchName = name
            performProductListByCategoryIdAndName(
                categoryId = categoryId,
                name = currentSearchName,
                isLoadMore = false,
            )
        }
    }

    fun actionSearch(name: String) {
        jobSearch?.cancel()
        currentSearchName = name
        performProductListByCategoryIdAndName(
            categoryId = categoryId,
            name = currentSearchName,
            isLoadMore = false,
        )
    }

    fun refresh() {
        _isRefresh.value = true
        performProductListByCategoryIdAndName(
            categoryId = categoryId,
            name = currentSearchName,
            isLoadMore = false,
        )
    }

    fun setCategoryId(categoryId: String?) {
        if (categoryId == null) return
        this.categoryId = categoryId
        performProductListByCategoryIdAndName(categoryId = categoryId, isLoadMore = false)
    }

    private fun performProductListByCategoryIdAndName(
        categoryId: String? = null,
        name: String? = null,
        isLoadMore: Boolean = false
    ) = launch {
        if (!isLoadMore) {
            nextOffset.reset()
        }
        discoveryRepository.getProductList(
            categoryId = categoryId.orEmpty(),
            name = name.orEmpty(),
            offset = nextOffset
        )
            .onSuccess {
                if (it.products.size < nextOffset.limit) {
                    productInfoAdapter.isLastPage(true)
                } else {
                    nextOffset.offset = it.pagination.offset
                }
                productInfoAdapter.submitData(it.products, extend = isLoadMore)
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
        if (_isRefresh.value == true) _isRefresh.value = false
    }

    fun loadMoreProduct() {
        performProductListByCategoryIdAndName(
            categoryId = categoryId,
            name = currentSearchName,
            isLoadMore = true,
        )
    }

    private fun onProductItemClick(item: ProductInfo) {
        navigateToProductDetail(item.productId)
    }

    fun navigateToProductDetail(productId: String) {
        navigator.navigateTo(
            SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(
                productId
            )
        )
    }

}