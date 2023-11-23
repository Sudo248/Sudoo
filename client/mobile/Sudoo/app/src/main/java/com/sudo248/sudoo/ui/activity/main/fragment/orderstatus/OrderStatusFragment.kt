package com.sudo248.sudoo.ui.activity.main.fragment.orderstatus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.visible
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.FragmentOrderStatusBinding
import com.sudo248.sudoo.domain.entity.order.OrderStatus
import com.sudo248.sudoo.ui.activity.main.adapter.OrderStatusAdapter
import com.sudo248.sudoo.ui.activity.main.fragment.discovery.DiscoveryViewModel
import com.sudo248.sudoo.ui.models.order.OrderStatusTab
import com.sudo248.sudoo.ui.models.review.ReviewListTab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderStatusFragment : BaseFragment<FragmentOrderStatusBinding, OrderStatusViewModel>() {

    override val viewModel: OrderStatusViewModel by viewModels()
    override fun initView() {
        binding.viewModel = viewModel
        binding.rcvOrderStatusPrepare.setHasFixedSize(true)
        binding.rcvOrderStatusPrepare.adapter = viewModel.orderStatusAdapter

        binding.rcvOrderStatusShipping.setHasFixedSize(true)
        binding.rcvOrderStatusShipping.adapter = viewModel.orderStatusAdapter

        binding.rcvOrderStatusReceived.setHasFixedSize(true)
        binding.rcvOrderStatusReceived.adapter = viewModel.orderStatusAdapter

        setupTabLayout()

        binding.refresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setupTabLayout() {
        binding.tabOrderStatus.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    if (it.position == 0) {
                        setPrepareItem(OrderStatusTab.PREPARE)
                        viewModel.onClickTabItem(OrderStatusTab.PREPARE)
                    } else if (it.position == 1) {
                        setPrepareItem(OrderStatusTab.SHIPPING)
                        viewModel.onClickTabItem(OrderStatusTab.SHIPPING)
                    } else {
                        setPrepareItem(OrderStatusTab.RECEIVED)
                        viewModel.onClickTabItem(OrderStatusTab.RECEIVED)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun observer() {
        viewModel.isRefresh.observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = it
        }
    }

    private fun setPrepareItem(orderStatusTab: OrderStatusTab) {
        when(orderStatusTab){
            OrderStatusTab.PREPARE -> {
                binding.rcvOrderStatusPrepare.visibility = View.VISIBLE
                binding.rcvOrderStatusShipping.visibility = View.GONE
                binding.rcvOrderStatusReceived.visibility = View.GONE
            }
            OrderStatusTab.SHIPPING -> {
                binding.rcvOrderStatusPrepare.visibility = View.GONE
                binding.rcvOrderStatusShipping.visibility = View.VISIBLE
                binding.rcvOrderStatusReceived.visibility = View.GONE
            }
            else ->{
                binding.rcvOrderStatusPrepare.visibility = View.GONE
                binding.rcvOrderStatusShipping.visibility = View.GONE
                binding.rcvOrderStatusReceived.visibility = View.VISIBLE
            }
        }
    }


}