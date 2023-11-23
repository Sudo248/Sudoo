package com.sudo248.sudoo.ui.activity.main.fragment.orderstatus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.FragmentOrderStatusBinding
import com.sudo248.sudoo.domain.entity.order.OrderStatus
import com.sudo248.sudoo.ui.activity.main.adapter.OrderStatusAdapter
import com.sudo248.sudoo.ui.activity.main.fragment.discovery.DiscoveryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderStatusFragment : BaseFragment<FragmentOrderStatusBinding, OrderStatusViewModel>() {

    override val viewModel: OrderStatusViewModel by viewModels()
    override fun initView() {
        binding.viewModel = viewModel
        binding.rcvOrderStatus.adapter = viewModel.orderStatusAdapter
    }

    override fun observer() {
        viewModel.isRefresh.observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = it
        }
    }


}