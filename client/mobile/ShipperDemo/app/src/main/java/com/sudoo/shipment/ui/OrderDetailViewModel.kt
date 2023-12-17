package com.sudoo.shipment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.base_android.navigation.IntentDirections
import com.sudoo.shipment.api.OrderService
import com.sudoo.shipment.model.Order
import com.sudoo.shipment.model.OrderStatus
import com.sudoo.shipment.model.PatchOrderSupplierDto
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val orderService: OrderService
): BaseViewModel<IntentDirections>() {

    var error: SingleEvent<String?> = SingleEvent(null)

    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    private val _orderStatus = MutableLiveData<OrderStatus>()
    val orderStatus: LiveData<OrderStatus> = _orderStatus

    fun getOrderSupplierDetail(orderSupplierId: String?) = launch(Dispatchers.IO) {
        if (orderSupplierId == null) {
            error = SingleEvent("Something went wrong")
            emitState(UiState.ERROR)
        } else {
            emitState(UiState.LOADING)
            handleResponse(orderService.getOrderById(orderSupplierId))
                .onSuccess {
                    val order = it.data!!
                    _order.postValue(order)
                    order.orderSuppliers.firstOrNull()?.let { orderSupplier ->
                        _orderStatus.postValue(orderSupplier.status)
                    }
                }
                .onError {
                    error = SingleEvent(it.message)
                }.bindUiState(_uiState)
        }
    }

    fun patchOrderStatus(status: OrderStatus) = launch(Dispatchers.IO) {
        _order.value?.orderSuppliers?.firstOrNull()?.orderSupplierId?.let { orderSupplierId ->
            emitState(UiState.LOADING)
            handleResponse(
                orderService.patchOrderSupplier(
                    orderSupplierId = orderSupplierId,
                    PatchOrderSupplierDto(status = status)
                )
            )
                .onSuccess {
                    _orderStatus.postValue(status)
                }
                .onError {
                    error = SingleEvent(it.message)
                }.bindUiState(_uiState)
        }
    }
}