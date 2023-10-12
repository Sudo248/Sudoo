package com.sudo248.sudoo.ui.activity.main.fragment.product_detail

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.databinding.FragmentProductDetailBinding
import com.sudo248.sudoo.ui.activity.main.MainActivity
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 15:52 - 20/03/2023
 */
@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding, ProductDetailViewModel>(), ViewController {
    override val viewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailFragmentArgs by navArgs()

    override val enableStateScreen: Boolean
        get() = true

    private val callbackManager = CallbackManager.Factory.create()

    override fun initView() {
        viewModel.product = args.product
        binding.viewModel = viewModel
        viewModel.viewController = this
        viewModel.getSupplierAddress()
        try {
            setupSendMessage()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setupSendMessage() {
        val content = ShareLinkContent.Builder().setContentUrl(Uri.parse(viewModel.getDeeplinkToProduction())).build()
        binding.btnSendMessage.shareContent = content
        binding.btnSendMessage.registerCallback(callbackManager, object : FacebookCallback<Sharer.Result> {
            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {

            }

            override fun onSuccess(result: Sharer.Result) {

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
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

    override fun setBadgeCart(amount: Int) {
        (requireActivity() as MainActivity).setBadgeCart(amount)
    }
}