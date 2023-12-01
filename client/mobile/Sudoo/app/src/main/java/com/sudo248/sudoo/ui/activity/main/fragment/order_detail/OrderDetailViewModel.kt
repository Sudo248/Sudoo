package com.sudo248.sudoo.ui.activity.main.fragment.order_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.data.dto.order.PatchOrderSupplierDto
import com.sudo248.sudoo.domain.entity.order.Order
import com.sudo248.sudoo.domain.entity.order.OrderStatus
import com.sudo248.sudoo.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : BaseViewModel<NavDirections>() {

    var error: SingleEvent<String?> = SingleEvent(null)

    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    private val _orderStatus = MutableLiveData<OrderStatus>()
    val orderStatus: LiveData<OrderStatus> = _orderStatus

    fun getOrderSupplierDetail(orderSupplierId: String) = launch {
        setState(UiState.LOADING)
        orderRepository.getOrderSupplierDetail(orderSupplierId)
            .onSuccess {
                _order.postValue(it)
                it.orderSuppliers.firstOrNull()?.let { orderSupplier ->
                    _orderStatus.postValue(orderSupplier.status)
                }
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    fun receivedOrder() = launch {
        _order.value?.orderSuppliers?.firstOrNull()?.orderSupplierId?.let { orderSupplierId ->
            setState(UiState.LOADING)
            orderRepository.patchOrderSupplier(
                orderSupplierId,
                PatchOrderSupplierDto(
                    status = OrderStatus.RECEIVED,
                    receivedDateTime = LocalDateTime.now()
                )
            )
                .onSuccess {
                    _orderStatus.postValue(OrderStatus.RECEIVED)
                }
                .onError {
                    error = SingleEvent(it.message)
                }.bindUiState(_uiState)
        }
    }

    fun back() {
        navigator.back()
    }

}