package com.sudo248.sudoo.ui.activity.main.fragment.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.domain.entity.cart.AddSupplierProduct
import com.sudo248.sudoo.domain.entity.cart.Cart
import com.sudo248.sudoo.domain.repository.CartRepository
import com.sudo248.sudoo.domain.repository.InvoiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val invoiceRepository: InvoiceRepository
) : BaseViewModel<NavDirections>() {

    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart> = _cart

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    var error: SingleEvent<String?> = SingleEvent(null)

    var viewController: ViewController? = null

    fun getActiveCart() = launch {
        setState(UiState.LOADING)
        cartRepository.getActiveCart()
            .onSuccess {
                _cart.postValue(it)
                _totalPrice.postValue(it.totalPrice)
            }
            .onError {
                it.printStackTrace()
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    fun updateSupplierProduct(addSupplierProduct: AddSupplierProduct) = launch {
        setState(UiState.LOADING)
        cartRepository.updateSupplierProduct(
            _cart.value!!.cartId,
            addSupplierProduct
        )
            .onSuccess {
                _totalPrice.postValue(it.totalPrice)
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    fun deleteItemFromCart(addSupplierProduct: AddSupplierProduct) = launch {
        setState(UiState.LOADING)
        cartRepository.deleteSupplierProduct(
            _cart.value!!.cartId,
            addSupplierProduct
        )
            .onSuccess {
                _cart.postValue(it)
                _totalPrice.postValue(it.totalPrice)
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    fun buy() = launch {
        emitState(UiState.LOADING)
        invoiceRepository.createInvoice(_cart.value!!.cartId)
            .onSuccess {
                viewController?.navigateToPayment(it)
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

}