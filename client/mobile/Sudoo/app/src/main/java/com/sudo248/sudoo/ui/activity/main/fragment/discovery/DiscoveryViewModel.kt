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
import com.sudo248.sudoo.ui.base.LoadMoreRecyclerViewListener
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

    private val _userImage = MutableLiveData<String>()
    val userImage: LiveData<String> = _userImage

    private val _categoryName = MutableLiveData("")
    val categoryName: LiveData<String> = _categoryName

    var error: SingleEvent<String?> = SingleEvent(null)

    val categoryAdapter = CategoryAdapter(::onCategoryItemClick)

    private val productLoadMore = object : LoadMoreRecyclerViewListener {
        override fun onLoadMore(page: Int, itemCount: Int) {
            loadMoreProduct()
        }
    }

    val productInfoAdapter = ProductInfoAdapter(::onProductItemClick, productLoadMore)

    init {
        getCategories()
    }

    private fun getCategories() = launch {
        emitState(UiState.LOADING)
        discoveryRepository.getCategories()
            .onSuccess { categories ->
                categoryAdapter.submitList(categories)
                performProductListByCategoryId(categories.first().categoryId)
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }


    private fun performProductListByCategoryId(categoryId: String, isLoadMore: Boolean = false) =
        launch {
            setState(UiState.LOADING)
            if (!isLoadMore) {
                nextOffset.reset()
            }
            discoveryRepository.getProductListByCategoryId(categoryId, nextOffset)
                .onSuccess {
                    productInfoAdapter.submitData(it.products, extend = isLoadMore)
                    if (it.products.size < nextOffset.limit) {
                        productInfoAdapter.isLastPage(true)
                    } else {
                        nextOffset.offset = it.pagination.offset
                    }
                }
                .onError {
                    error = SingleEvent(it.message)
                }.bindUiState(_uiState)
        }

    private fun onCategoryItemClick(item: Category) =
        performProductListByCategoryId(item.categoryId)

    private fun onProductItemClick(item: ProductInfo) {
        navigateToProductDetail(item.productId)
    }

    private fun loadMoreProduct() {
        val currentCategory = categoryAdapter.getCurrentSelectedCategory()
        performProductListByCategoryId(currentCategory.categoryId, isLoadMore = true)
    }

    fun navigateToSearchView() {
        navigator.navigateTo(DiscoveryFragmentDirections.actionDiscoveryFragmentToSearchFragment())
    }

    fun navigateToProductDetail(productId: String) {
        navigator.navigateTo(
            DiscoveryFragmentDirections.actionDiscoveryFragmentToProductDetailFragment(
                productId
            )
        )
    }
}