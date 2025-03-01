package com.sudo248.sudoo.ui.activity.main.fragment.order_list

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.databinding.FragmentOrderStatusBinding
import com.sudo248.sudoo.ui.activity.main.MainActivity
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import com.sudo248.sudoo.ui.models.order.OrderStatusTab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListFragment : BaseFragment<FragmentOrderStatusBinding, OrderListViewModel>() {
    override val enableStateScreen: Boolean = true
    override val viewModel: OrderListViewModel by viewModels()
    private val args: OrderListFragmentArgs by navArgs()

    override fun initView() {
        binding.viewModel = viewModel
        (activity as MainActivity).run {
            binding.imgBack.isVisible = args.isHideBottomNav
            if (args.isHideBottomNav) {
                goneBottomNav()
            } else {
                showBottomNav()
            }
        }
        binding.rcvOrders.setHasFixedSize(true)
        binding.rcvOrders.adapter = viewModel.orderListAdapter

        setupTabLayout()

        binding.refresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setupTabLayout() {
        binding.tabOrderStatus.getTabAt(viewModel.currentTab.ordinal)?.select()
        binding.tabOrderStatus.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewModel.onClickTabItem(OrderStatusTab.values()[it.position])
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

    override fun onStateError() {
        super.onStateError()
        viewModel.error.run {
            val message = getValueIfNotHandled()
            if (!message.isNullOrEmpty()) {
                DialogUtils.showErrorDialog(
                    context = requireContext(),
                    message = message
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.reset()
    }
}