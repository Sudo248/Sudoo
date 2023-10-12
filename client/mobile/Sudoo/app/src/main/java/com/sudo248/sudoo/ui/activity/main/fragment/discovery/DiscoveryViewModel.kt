package com.sudo248.sudoo.ui.activity.main.fragment.discovery

import android.util.Log
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
import com.sudo248.sudoo.domain.repository.DiscoveryRepository
import com.sudo248.sudoo.ui.activity.main.adapter.CategoryAdapter
import com.sudo248.sudoo.ui.activity.main.adapter.ProductAdapter
import com.sudo248.sudoo.ui.mapper.toCategory
import com.sudo248.sudoo.ui.mapper.toCategoryUi
import com.sudo248.sudoo.ui.mapper.toListProductUi
import com.sudo248.sudoo.ui.uimodel.CategoryUiModel
import com.sudo248.sudoo.ui.uimodel.ProductUiModel
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
    private val _userImage = MutableLiveData<String>()
    val userImage: LiveData<String> = _userImage

    private val _categoryName = MutableLiveData<String>("")
    val categoryName: LiveData<String> = _categoryName

    var error: SingleEvent<String?> = SingleEvent(null)

    val categoryAdapter = CategoryAdapter(::onCategoryItemClick)
    private val categories: MutableList<Category> = mutableListOf()

    val productAdapter = ProductAdapter(::onProductItemClick)

    init {
        launch {
            discoveryRepository.getCategoryInfo()
                .onSuccess { categoryInfo ->
                    categories.addAll(categoryInfo.map { it.toCategory() })
                    categoryAdapter.submitList(getListCategoryUi())
                    productAdapter.submitList(getListProductFromCategory(categories.first()))
                }
                .onError {
                    error = SingleEvent(it.message)
                }.bindUiState(_uiState)
        }
    }

    private fun getListCategoryUi(selectedItemIndex: Int = 0): List<CategoryUiModel> {
        return categories.map { it.toCategoryUi() }
    }

    private suspend fun getListProductUiByCategoryId(categoryId: String): List<ProductUiModel> {
        return getListProductFromCategory(categories.first { it.categoryId == categoryId })
    }

    private suspend fun getListProductFromCategory(category: Category): List<ProductUiModel> {
        _categoryName.postValue(category.name)
        if (category.products.isEmpty()) {
            setState(UiState.LOADING)
            discoveryRepository.getCategoryById(category.categoryId)
                .onSuccess { category.products = it.products }
                .onError {
                    error = SingleEvent(it.message)
                }.bindUiState(_uiState)
        }
        return category.products.flatMap { it.toListProductUi() }
    }

    private fun onCategoryItemClick(item: CategoryUiModel) = launch {
        setState(UiState.LOADING)
        productAdapter.submitList(getListProductUiByCategoryId(item.categoryId))
        setState(UiState.SUCCESS)
    }

    private fun onProductItemClick(item: ProductUiModel) {
        navigator.navigateTo(
            DiscoveryFragmentDirections.actionDiscoveryFragmentToProductDetailFragment(
                item
            )
        )
    }

    fun navigateToSearchView() {
        navigator.navigateTo(DiscoveryFragmentDirections.actionDiscoveryFragmentToSearchFragment())
    }

    fun getProductById(productId: String) = launch {
        emitState(UiState.LOADING)
        discoveryRepository.getProductById(productId)
            .onSuccess {
                navigator.navigateTo(
                    DiscoveryFragmentDirections.actionDiscoveryFragmentToProductDetailFragment(
                        it.toListProductUi()[0]
                    )
                )
                emitState(UiState.SUCCESS)
            }
            .onError {
                error = SingleEvent(it.message)
                emitState(UiState.ERROR)
            }
    }
}