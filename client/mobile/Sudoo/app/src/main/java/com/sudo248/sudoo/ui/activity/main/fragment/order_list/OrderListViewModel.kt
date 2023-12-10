package com.sudo248.sudoo.ui.activity.main.fragment.order_list

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
import com.sudo248.sudoo.domain.entity.order.OrderStatus
import com.sudo248.sudoo.domain.entity.order.OrderSupplierInfo
import com.sudo248.sudoo.domain.repository.OrderRepository
import com.sudo248.sudoo.ui.activity.main.adapter.OrderListAdapter
import com.sudo248.sudoo.ui.models.order.OrderStatusTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : BaseViewModel<NavDirections>() {

    var error: SingleEvent<String?> = SingleEvent(null)

    var currentTab: OrderStatusTab = OrderStatusTab.PREPARE

    private val _isEmptyOrder = MutableLiveData(false)
    val isEmptyOrder: LiveData<Boolean> = _isEmptyOrder

    private val orders = hashMapOf<OrderStatusTab, List<OrderSupplierInfo>?>(
        Pair(OrderStatusTab.PREPARE, null),
        Pair(OrderStatusTab.TAKE_ORDER, null),
        Pair(OrderStatusTab.SHIPPING, null),
        Pair(OrderStatusTab.RECEIVED, null),
    )

    val orderListAdapter = OrderListAdapter(
        onItemClick = {
            navigator.navigateTo(
                OrderListFragmentDirections.actionOrderStatusFragmentToOrderDetailFragment(
                    it.orderSupplierId
                )
            )
        },
        onNextAction = ::handleOnNextAction
    )

    private val _isRefresh = MutableLiveData(false)
    val isRefresh: LiveData<Boolean> = _isRefresh

    init {
        refresh()
    }

    fun refresh() {
        _isRefresh.value = true
        onClickTabItem(currentTab)
    }

    private fun getOrderSuppliers(statusValue: String) = launch {
        if (_isRefresh.value == true || orders[currentTab] == null) {
            orderRepository.getListOrderSupplierByStatus(statusValue)
                .onSuccess { orderSupplier ->
                    orders[currentTab] = orderSupplier
                    orderListAdapter.submitList(orders[currentTab])
                    _isEmptyOrder.postValue(orders[currentTab].isNullOrEmpty())
                    _isRefresh.value = false
                }
                .onError {
                    _isEmptyOrder.postValue(true)
                    error = SingleEvent(it.message)
                    _isRefresh.value = false
                }
        } else {
            orderListAdapter.submitList(orders[currentTab])
            _isEmptyOrder.postValue(orders[currentTab].isNullOrEmpty())
        }
    }

    private fun handleOnNextAction(orderSupplier: OrderSupplierInfo) {
        if (orderSupplier.status == OrderStatus.RECEIVED) {
            navigator.navigateTo(OrderListFragmentDirections.actionOrderStatusFragmentToReviewListFragment())
        } else {
            receivedOrder(orderSupplier)
        }
    }

    private fun receivedOrder(orderSupplier: OrderSupplierInfo) = launch {
        setState(UiState.LOADING)
        orderRepository.patchOrderSupplier(
            orderSupplier.orderSupplierId,
            PatchOrderSupplierDto(
                status = OrderStatus.RECEIVED,
                receivedDateTime = LocalDateTime.now()
            )
        )
            .onSuccess {
                orderSupplier.status = OrderStatus.RECEIVED
                val receivedOrderList = orders[OrderStatusTab.RECEIVED].orEmpty().toMutableList()
                val shippingOrderList = orders[OrderStatusTab.SHIPPING].orEmpty().toMutableList()
                receivedOrderList.add(orderSupplier)
                shippingOrderList.remove(orderSupplier)
                orders[OrderStatusTab.RECEIVED] = receivedOrderList
                orders[OrderStatusTab.SHIPPING] = shippingOrderList
                orderListAdapter.submitList(orders[currentTab])
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    fun onClickTabItem(tab: OrderStatusTab) {
        currentTab = tab
        getOrderSuppliers(tab.statusValue)
    }
}