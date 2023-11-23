package com.sudo248.sudoo.ui.activity.main.fragment.orderstatus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.data.dto.order.OrderSupplierUserInfoDto
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

    private val _orderSuppliers = MutableLiveData<List<OrderSupplierUserInfoDto>>()
    val orderSuppliers: LiveData<List<OrderSupplierUserInfoDto>> = _orderSuppliers

    val orderStatusAdapter = OrderStatusAdapter(_orderSuppliers.value ?: listOf())

    private val _isRefresh = MutableLiveData(false)
    val isRefresh: LiveData<Boolean> = _isRefresh

    init {
        refresh()

    }


    fun refresh() {
        _isRefresh.value = true
        getOrderSuppliers()
    }

    fun getOrderSuppliers() = launch {
        orderRepository.getOrderByStatus(OrderStatus.TAKE_ORDER.name)
            .onSuccess {
                orderStatusAdapter.submitList(it.orderSuppliers)
            }
            .onError {

            }

    }


}