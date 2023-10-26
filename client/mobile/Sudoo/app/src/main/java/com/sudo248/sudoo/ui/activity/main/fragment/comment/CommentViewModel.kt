package com.sudo248.sudoo.ui.activity.main.fragment.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.domain.entity.discovery.ProductInfo
import com.sudo248.sudoo.domain.entity.discovery.UpsertComment
import com.sudo248.sudoo.domain.repository.DiscoveryRepository
import com.sudo248.sudoo.domain.repository.ImageRepository
import com.sudo248.sudoo.ui.activity.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val discoveryRepository: DiscoveryRepository,
    private val imageRepository: ImageRepository
) : BaseViewModel<NavDirections>() {

    private var viewController: ViewController? = null

    private lateinit var parentViewModel: MainViewModel

    private val _productInfo = MutableLiveData<ProductInfo>()
    val productInfo: LiveData<ProductInfo> = _productInfo

    var error: SingleEvent<String?> = SingleEvent(null)

    fun setViewController(viewController: ViewController) {
        this.viewController = viewController
    }

    fun getProduct(productId: String) = launch {
        emitState(UiState.LOADING)
        discoveryRepository.getProductInfo(productId)
            .onSuccess {
                _productInfo.value = it
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    fun upsertComment() = launch {
        viewController?.getUpsertComment()?.let { upsertComment ->
            emitState(UiState.LOADING)
            parentViewModel.imageUri.value?.let {
                viewController?.run {
                    val imageUrl = imageRepository.uploadImage(getPathImageFromUri(it)).get()
                    upsertComment.images = listOf(imageUrl)
                }
            }

            discoveryRepository.upsertComment(upsertComment)
                .onSuccess {
                    parentViewModel.setImageUri(null)
                    navigator.back()
                }
                .onError {
                    error = SingleEvent(it.message)
                }.bindUiState(_uiState)
        }
    }



    fun pickImage() {
        parentViewModel.pickImage()
    }

    fun takeImage() {
        parentViewModel.takeImage()
    }

    fun deleteImage() {
        parentViewModel.setImageUri(null)
    }

    fun back() {
        navigator.back()
    }

    override fun onCleared() {
        super.onCleared()
        viewController = null
        parentViewModel.setImageUri(null)
    }
}