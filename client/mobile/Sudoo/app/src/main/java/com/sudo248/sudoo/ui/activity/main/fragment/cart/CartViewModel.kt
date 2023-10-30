package com.sudo248.sudoo.ui.activity.main.fragment.cart

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.domain.entity.cart.AddCartProduct
import com.sudo248.sudoo.domain.entity.cart.AddCartProducts
import com.sudo248.sudoo.domain.entity.cart.Cart
import com.sudo248.sudoo.domain.repository.CartRepository
import com.sudo248.sudoo.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository
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
                updateTotalPrice(it)
            }
            .onError {
                it.printStackTrace()
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    fun updateProduct(addCartProduct: AddCartProduct) = launch {
        setState(UiState.LOADING)
        cartRepository.addProductToActiveCart(addCartProduct).onSuccess {
            updateTotalPrice(it)
        }.onError {
            error = SingleEvent(it.message)
        }.bindUiState(_uiState)
    }

    fun deleteItemFromCart(addCartProduct: AddCartProduct) = launch {
//        setState(UiState.LOADING)
//        cartRepository.deleteSupplierProduct(
//            _cart.value!!.cartId,
//            addCartProduct
//        )
//            .onSuccess {
//                _cart.postValue(it)
//                _totalPrice.postValue(it.totalPrice)
//            }
//            .onError {
//                error = SingleEvent(it.message)
//            }.bindUiState(_uiState)
    }

    fun buy() = launch {
        emitState(UiState.LOADING)
        orderRepository.createOrder(_cart.value!!.cartId)
            .onSuccess {
                Log.i("CART_ID", it)
                viewController?.navigateToPayment(it)
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
//        invoiceRepository.createInvoice(_cart.value!!.cartId)

    }

    private fun updateTotalPrice(cart: Cart) {
        var totalPrice = 0.0f
        for (cartProduct in cart.cartProducts) {
            totalPrice += (cartProduct.product?.price ?: 0.0f) * (cartProduct.quantity)
        }
        _totalPrice.postValue(totalPrice * 1.0)
    }

    override fun onCleared() {
        super.onCleared()
        viewController = null
    }


}