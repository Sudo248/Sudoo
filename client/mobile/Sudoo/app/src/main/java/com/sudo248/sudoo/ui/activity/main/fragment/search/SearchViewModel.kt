package com.sudo248.sudoo.ui.activity.main.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.domain.repository.DiscoveryRepository
import com.sudo248.sudoo.ui.mapper.toListProductUi
import com.sudo248.sudoo.ui.uimodel.ProductUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val discoveryRepository: DiscoveryRepository
) : BaseViewModel<NavDirections>() {

    private val _products = MutableLiveData<List<ProductUiModel>>(listOf())
    val products: LiveData<List<ProductUiModel>> = _products

    fun search(name: String) = launch {
        setState(UiState.LOADING)
        discoveryRepository.searchProductByName(name)
            .onSuccess { products ->
                _products.postValue(products.flatMap { it.toListProductUi() })
            }
            .onError {

            }
            .bindUiState(_uiState)
    }

}