package com.sudo248.sudoo.ui.activity.main.fragment.discovery

import android.util.Log
import androidx.fragment.app.viewModels
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.databinding.FragmentDiscoveryBinding
import com.sudo248.sudoo.ui.activity.main.DeepLinkHandler
import com.sudo248.sudoo.ui.activity.main.MainActivity
import com.sudo248.sudoo.ui.ktx.setHorizontalViewPort
import com.sudo248.sudoo.ui.ktx.showErrorDialog
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

        handleDeeplink()

        binding.rcvCategories.setHasFixedSize(true)
//        binding.rcvCategories.setHorizontalViewPort(3.5f)
        binding.rcvCategories.adapter = viewModel.categoryAdapter

        binding.rcvProducts.setHasFixedSize(true)
        binding.rcvProducts.adapter = viewModel.productAdapter

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
                    viewModel.getProductById(productId)
                }
            })
            activity?.intent?.data?.path?.let {
                isHandlerDeeplink = true
                val productId = it.substringAfterLast("/").substringBefore("?")
                Log.d("Sudoo", "handleDeeplink: productId: $productId")
                viewModel.getProductById(productId)
            }
        }
    }
}