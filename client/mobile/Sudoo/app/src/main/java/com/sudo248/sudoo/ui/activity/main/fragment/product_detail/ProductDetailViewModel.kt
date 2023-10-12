package com.sudo248.sudoo.ui.activity.main.fragment.product_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.domain.entity.cart.AddSupplierProduct
import com.sudo248.sudoo.domain.repository.CartRepository
import com.sudo248.sudoo.domain.repository.DiscoveryRepository
import com.sudo248.sudoo.ui.uimodel.ProductUiModel
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

    private val _supplierLocation =MutableLiveData("")
    val supplierLocation: LiveData<String> = _supplierLocation

    var error: SingleEvent<String?> = SingleEvent(null)
    // Khi khởi tạo view observer dữ liệu trong viewmodel nên sẽ hiển thị sai. Khi set lại value nhưng k notify nên k có dữ liệu
    var product: ProductUiModel = ProductUiModel()

    fun getSupplierAddress() = launch {
        discoveryRepository.getSupplierAddress(product.supplierId)
            .onSuccess {
                _supplierLocation.postValue(it.fullAddress)
            }
    }

    fun addSupplierProduct() = launch {
        val addSupplierProduct = AddSupplierProduct(
            product.supplierId,
            product.productId,
            1
        )
        setState(UiState.LOADING)
        cartRepository.addSupplierProduct(addSupplierProduct)
            .onSuccess {
                viewController?.setBadgeCart(it.cartSupplierProducts.size)
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    fun onClickLike() {
        product.isLike.set(!product.isLike.get()!!)
        product.isLike.notifyChange()
    }

    fun onBack() {
        navigator.back()
    }

    fun openChat() {
        navigator.navigateTo(ProductDetailFragmentDirections.actionProductDetailFragmentToChatFragment(product.supplierId))
    }

    fun buyNow() = launch {
        addSupplierProduct().join()
        navigator.navigateOff(ProductDetailFragmentDirections.actionProductDetailFragmentToCartFragment())
    }

    fun getDeeplinkToProduction(): String {
        return "${BuildConfig.BASE_URL}discovery/product/share/${product.productId}"
    }

}