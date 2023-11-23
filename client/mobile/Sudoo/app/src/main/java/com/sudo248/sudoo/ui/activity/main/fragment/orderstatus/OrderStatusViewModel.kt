package com.sudo248.sudoo.ui.activity.main.fragment.orderstatus

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.data.dto.order.OrderSupplierUserInfoDto
import com.sudo248.sudoo.domain.entity.order.Order
import com.sudo248.sudoo.domain.entity.order.OrderCartProduct
import com.sudo248.sudoo.domain.entity.order.OrderStatus
import com.sudo248.sudoo.domain.entity.order.OrderSupplier
import com.sudo248.sudoo.domain.repository.OrderRepository
import com.sudo248.sudoo.domain.repository.PaymentRepository
import com.sudo248.sudoo.ui.activity.main.adapter.OrderStatusAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderStatusViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : BaseViewModel<NavDirections>() {

    var error: SingleEvent<String?> = SingleEvent(null)

    private val _orderSuppliers = MutableLiveData<List<OrderCartProduct>>()
    val orderSuppliers: LiveData<List<OrderCartProduct>> = _orderSuppliers

    val orderStatusAdapter = OrderStatusAdapter()

    private val _isRefresh = MutableLiveData(false)
    val isRefresh: LiveData<Boolean> = _isRefresh

    init {
        refresh()
    }


    fun refresh() {
        _isRefresh.value = true
        getOrderSuppliers()
    }

    private fun getOrderSuppliers() = launch {
        orderRepository.getOrderByStatus(OrderStatus.PREPARE.name)
            .onSuccess {
                orderStatusAdapter.submitList(it[0].orderSuppliers[0].orderCartProducts)
                _isRefresh.value = false
            }
            .onError {
                error = SingleEvent(it.message)
                _isRefresh.value = false
            }.bindUiState(_uiState)

    }


}