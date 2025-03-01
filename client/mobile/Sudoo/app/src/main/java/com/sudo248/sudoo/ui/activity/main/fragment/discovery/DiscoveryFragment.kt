package com.sudo248.sudoo.ui.activity.main.fragment.discovery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.visible
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.FragmentDiscoveryBinding
import com.sudo248.sudoo.ui.activity.main.DeepLinkHandler
import com.sudo248.sudoo.ui.activity.main.MainActivity
import com.sudo248.sudoo.ui.base.EndlessNestedScrollListener
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import com.sudo248.sudoo.ui.ktx.toSlideModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:34 - 12/03/2023
 */
@AndroidEntryPoint
class DiscoveryFragment : BaseFragment<FragmentDiscoveryBinding, DiscoveryViewModel>() {
    override val viewModel: DiscoveryViewModel by viewModels()
    override val enableStateScreen: Boolean = true

    private var isHandlerDeeplink = false

    override fun initView() {
        binding.viewModel = viewModel

//        handleDeeplink()

        binding.rcvCategories.setHasFixedSize(true)
        binding.rcvCategories.adapter = viewModel.categoryAdapter

        binding.rcvProducts.setHasFixedSize(true)
        binding.rcvProducts.adapter = viewModel.productInfoAdapter

        binding.parentScrollView.setOnScrollChangeListener(viewModel.endlessNestedScrollListener)

        binding.header.search.setOnClickListener {
            viewModel.navigateToSearchView()
        }

        binding.refresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    override fun observer() {
        viewModel.isRefresh.observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = it
        }
        viewModel.banners.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.slideshowBanner.visible()
                binding.slideshowBanner.setImageList(it.map { url -> url.toSlideModel() }, ScaleTypes.CENTER_INSIDE)
            } else {
                binding.slideshowBanner.gone()
            }
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

    private fun handleDeeplink() {
        if (!isHandlerDeeplink) {
            (activity as MainActivity).setDeepLinkHandler(object : DeepLinkHandler {
                override fun onHandle(link: String) {
                    isHandlerDeeplink = true
                    val productId = link.substringAfterLast("/").substringBefore("?")
                    Log.d("Sudoo", "handleDeeplink: productId: $productId")
                    viewModel.navigateToProductDetail(productId)
                }
            })
            activity?.intent?.data?.path?.let {
                isHandlerDeeplink = true
                val productId = it.substringAfterLast("/").substringBefore("?")
                Log.d("Sudoo", "handleDeeplink: productId: $productId")
                viewModel.navigateToProductDetail(productId)
            }
        }
    }
}