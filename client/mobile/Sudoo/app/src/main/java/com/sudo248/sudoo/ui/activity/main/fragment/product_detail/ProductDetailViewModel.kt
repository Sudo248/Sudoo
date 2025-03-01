package com.sudo248.sudoo.ui.activity.main.fragment.product_detail

import android.Manifest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.createActionIntentDirections
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.R
import com.sudo248.sudoo.domain.entity.cart.AddCartProduct
import com.sudo248.sudoo.domain.entity.discovery.Offset
import com.sudo248.sudoo.domain.entity.discovery.Product
import com.sudo248.sudoo.domain.entity.discovery.SupplierInfo
import com.sudo248.sudoo.domain.repository.CartRepository
import com.sudo248.sudoo.domain.repository.DiscoveryRepository
import com.sudo248.sudoo.ui.activity.ar.ArActivity
import com.sudo248.sudoo.ui.activity.main.MainViewModel
import com.sudo248.sudoo.ui.activity.main.adapter.CommentAdapter
import com.sudo248.sudoo.ui.util.BundleKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 15:52 - 20/03/2023
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val discoveryRepository: DiscoveryRepository,
    private val cartRepository: CartRepository
) : BaseViewModel<NavDirections>() {

    var viewController: ViewController? = null

    lateinit var parentViewModel: MainViewModel

    var error: SingleEvent<String?> = SingleEvent(null)

    private val _isRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRefresh: LiveData<Boolean> = _isRefresh

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val _supplier = MutableLiveData<SupplierInfo>()
    val supplier: LiveData<SupplierInfo> = _supplier

    private val _totalComment: MutableLiveData<Int> = MutableLiveData(0)
    val totalComment: LiveData<Int> = _totalComment

    private val _remainComment: MutableLiveData<Int> = MutableLiveData(0)
    val remainComment: LiveData<Int> = _remainComment

    private val _enableArViewer: MutableLiveData<Boolean> = MutableLiveData(false)
    val enableArViewer: LiveData<Boolean> = _enableArViewer

    val commentAdapter: CommentAdapter by lazy { CommentAdapter() }

    private var nextOffset: Offset = Offset()

    fun refresh() {
        _isRefresh.value = true
        _product.value?.let {
            getProduct(it.productId)
        }
    }

    fun getProduct(productId: String) = launch {
        if (_isRefresh.value == false) emitState(UiState.LOADING)
        discoveryRepository.getProductDetail(productId)
            .onSuccess {
                _product.postValue(it)
                it.supplier?.let { supplierInfo ->
                    _supplier.value = supplierInfo
                }
                _enableArViewer.postValue(it.extras.enableArViewer)
                getComments(it.productId)
            }
            .onError {
                error = SingleEvent(it.message)
            }.run {
                if (_isRefresh.value == true) bindUiState(_uiState)
            }
        _isRefresh.value = false
    }

    private fun getComments(productId: String, isLoadMore: Boolean = false) = launch {
        if (!isLoadMore) {
            nextOffset.reset()
        }
        discoveryRepository.getCommentsOfProduct(productId, offset = nextOffset)
            .onSuccess {
                if (it.comments.size < nextOffset.limit) {
                    _remainComment.value = 0
                } else {
                    nextOffset.offset = it.pagination.offset
                    _remainComment.value =
                        it.pagination.total - (commentAdapter.currentList.size + nextOffset.limit)
                }
                _totalComment.value = it.pagination.total
                commentAdapter.submitData(it.comments, extend = isLoadMore)
            }
            .onError {
                error = SingleEvent(it.message)
            }
    }

    fun countItemInCart() = launch {
        cartRepository.countItemInActiveCart()
            .onSuccess {
                viewController?.setBadgeCart(it)
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)

    }

    fun addProductToCart() = launch {
        getCartProduct()?.let { cartProduct ->
            setState(UiState.LOADING)
            cartRepository.addProductToActiveCart(cartProduct)
                .onSuccess { cart ->
                    viewController?.toast(R.string.add_to_cart_success)
                    viewController?.setBadgeCart(cart.quantity)
                }
                .onError {
                    error = SingleEvent(it.message)
                }.bindUiState(_uiState)
        }
    }

    private fun getCartProduct(): AddCartProduct? {
        return try {
            AddCartProduct(
                productId = _product.value!!.productId,
                amount = 1
            )
        } catch (e: NullPointerException) {
            error = SingleEvent("Some thing went wrong")
            setState(UiState.ERROR)
            null
        }
    }

    fun canAddToCart(): Boolean {
        return product.value != null && product.value!!.saleable && product.value!!.amount > 0
    }

    fun onBack() {
        navigator.back()
    }

    fun navigateToCart() {
        navigator.navigateTo(
            ProductDetailFragmentDirections.actionProductDetailFragmentToCartFragment(
                isHideBottomNav = true
            )
        )
    }

    fun navigateToHome() {
        navigator.navigateOffAll(ProductDetailFragmentDirections.actionProductDetailFragmentToDiscoveryFragment())
    }

    fun navigateToChat() {

    }

    fun buyNow() = launch {
        if (canAddToCart()) {
            addProductToCart().join()
        } else {
            product.value?.supplier?.let {
                viewController?.openContact(it.contactUrl)
            }

        }
    }

    fun getDeeplinkToProduction(): String {
        return ""
//        return "${BuildConfig.BASE_URL}discovery/product/share/${product.productId}"
    }

    fun loadMoreComment() {
        _product.value?.let {
            getComments(it.productId, isLoadMore = true)
        }
    }

    fun navigateToArView() {
        parentViewModel.requestPermission(Manifest.permission.CAMERA) {
            if (it) {
                _product.value?.extras?.source?.let { source ->
                    parentViewModel.navigator()
                        .navigateTo(ArActivity::class.createActionIntentDirections {
                            putExtra(BundleKeys.SOURCE_AR, source)
                        })
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewController = null
    }

}