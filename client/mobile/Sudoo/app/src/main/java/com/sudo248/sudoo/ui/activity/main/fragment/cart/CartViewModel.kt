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
import com.sudo248.sudoo.domain.entity.cart.AddCartProduct
import com.sudo248.sudoo.domain.entity.cart.AddCartProducts
import com.sudo248.sudoo.domain.entity.cart.Cart
import com.sudo248.sudoo.domain.entity.cart.CartProduct
import com.sudo248.sudoo.domain.repository.CartRepository
import com.sudo248.sudoo.ui.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : BaseViewModel<NavDirections>() {

    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart> = _cart

    private val _totalPrice = MutableLiveData(Utils.formatVnCurrency(0.0))
    val totalPrice: LiveData<String> = _totalPrice

    private val selectedCartProducts: MutableList<CartProduct> = mutableListOf()

    var error: SingleEvent<String?> = SingleEvent(null)

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
        setState(UiState.LOADING)
        cartRepository.deleteProductInCart(
            _cart.value!!.cartId,
            addCartProduct.productId
        )
            .onSuccess {
                _cart.postValue(it)
                updateTotalPrice(it)
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    fun buy() = launch {
        emitState(UiState.LOADING)
        cartRepository.createProcessingCart(getAddCartProducts())
            .onSuccess {
                navigator.navigateTo(CartFragmentDirections.actionCartFragmentToOrderFragment(cartId = it.cartId))
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    private fun updateTotalPrice(cart: Cart) {
        _totalPrice.postValue(Utils.formatVnCurrency(cart.totalPrice))
    }

    fun onClickCheckBox(isChecked: Boolean, cartProduct: CartProduct) {
        if (isChecked && !selectedCartProducts.contains(cartProduct)) {
            selectedCartProducts.add(cartProduct)
        } else {
            selectedCartProducts.remove(cartProduct)
        }
    }

    private fun getAddCartProducts(): AddCartProducts {
        return AddCartProducts(_cart.value!!.cartProducts)
    }

    fun back() {
        navigator.back()
    }
}