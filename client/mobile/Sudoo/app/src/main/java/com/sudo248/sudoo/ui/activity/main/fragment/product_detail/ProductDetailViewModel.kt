package com.sudo248.sudoo.ui.activity.main.fragment.product_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.domain.entity.discovery.Product
import com.sudo248.sudoo.domain.entity.discovery.Supplier
import com.sudo248.sudoo.domain.repository.CartRepository
import com.sudo248.sudoo.domain.repository.DiscoveryRepository
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

    var error: SingleEvent<String?> = SingleEvent(null)

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val _supplier = MutableLiveData<Supplier>()
    val supplier: LiveData<Supplier> = _supplier

    fun getProduct(productId: String) = launch {
        discoveryRepository.getProductDetail(productId)
            .onSuccess {
                _product.value = it
                it.supplier?.let {supplierInfo ->
                    getSupplierDetail(supplierInfo.supplierId)
                }
            }
            .onError {
                error = SingleEvent(it.message)
            }
    }

    private fun getSupplierDetail(supplierId: String) = launch {
        discoveryRepository.getSupplierDetail(supplierId)
            .onSuccess {
                _supplier.value = it
            }
            .onError {
                error = SingleEvent(it.message)
            }
    }

    fun addProductToCart() = launch {
//        val addProductToCart = AddSupplierProduct(
//            product.supplierId,
//            product.productId,
//            1
//        )
//        setState(UiState.LOADING)
//        cartRepository.addProductToCart(addProductToCart)
//            .onSuccess {
//                viewController?.setBadgeCart(it.cartSupplierProducts.size)
//            }
//            .onError {
//                error = SingleEvent(it.message)
//            }.bindUiState(_uiState)
    }

    fun onClickLike() {
//        product.isLike.set(!product.isLike.get()!!)
//        product.isLike.notifyChange()
    }

    fun onBack() {
        navigator.back()
    }

    fun openChat() {
//        navigator.navigateTo(ProductDetailFragmentDirections.actionProductDetailFragmentToChatFragment(product.supplierId))
    }

    fun buyNow() = launch {
        addProductToCart().join()
        navigator.navigateOff(ProductDetailFragmentDirections.actionProductDetailFragmentToCartFragment())
    }

    fun getDeeplinkToProduction(): String {
        return ""
//        return "${BuildConfig.BASE_URL}discovery/product/share/${product.productId}"
    }

}