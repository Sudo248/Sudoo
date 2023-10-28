package com.sudo248.sudoo.ui.activity.main.fragment.product_detail

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.ShareLinkContent
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.invisible
import com.sudo248.base_android.ktx.visible
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.databinding.FragmentProductDetailBinding
import com.sudo248.sudoo.domain.entity.discovery.Product
import com.sudo248.sudoo.domain.entity.discovery.SupplierInfo
import com.sudo248.sudoo.ui.activity.main.MainActivity
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import com.sudo248.sudoo.ui.util.Utils
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

    override val enableStateScreen: Boolean = true

    private val callbackManager = CallbackManager.Factory.create()

    override fun initView() {
        binding.viewModel = viewModel
        viewModel.viewController = this
        binding.rcvComment.adapter = viewModel.commentAdapter
        viewModel.getProduct(args.productId)
        binding.refreshProductDetail.setOnRefreshListener {
            viewModel.refresh()
        }
        try {
            setupSendMessage()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observer() {
        viewModel.isRefresh.observe(viewLifecycleOwner) {
            binding.refreshProductDetail.isRefreshing = it
        }

        viewModel.product.observe(viewLifecycleOwner) {
            performProduct(it)
        }

        viewModel.supplier.observe(viewLifecycleOwner) {
            performSupplier(it)
        }
    }

    private fun performProduct(product: Product) {
        binding.apply {
            loadImage(imgProductDetail, product.images.first())
            txtNameProduct.text = product.name
            txtPrice.text = Utils.formatVnCurrency(product.price)
            if (product.price < product.listedPrice) {
                txtListedPrice.visible()
                txtListedPrice.text = Utils.formatVnCurrency(product.listedPrice)
                txtListedPrice.paintFlags = txtListedPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                txtListedPrice.invisible()
            }

            if (product.discount > 0) {
                txtDiscountPercent.visible()
                txtDiscountPercent.text = Utils.formatDiscountPercent(product.discount)
            } else {
                txtDiscountPercent.invisible()
            }
            setRating(product.rate)
            txtNumberSold.text = Utils.formatSold(product.soldAmount)
            txtDescription.text = product.description
        }
    }

    private fun performSupplier(supplier: SupplierInfo) {
        binding.apply {
            loadImage(imgAvatarSupplier, supplier.avatar)
            txtSupplierRate.text = Utils.format(supplier.rate.toDouble(), digit = 1)
        }
    }

    private fun setRating(rating: Float) {
        binding.rating.rating = rating
        binding.txtNumberStart.text = Utils.format(rating.toDouble(), digit = 1)
    }

    private fun setupSendMessage() {
        val content =
            ShareLinkContent.Builder().setContentUrl(Uri.parse(viewModel.getDeeplinkToProduction()))
                .build()
        binding.btnSendMessage.shareContent = content
        binding.btnSendMessage.registerCallback(
            callbackManager,
            object : FacebookCallback<Sharer.Result> {
                override fun onCancel() {

                }

                override fun onError(error: FacebookException) {

                }

            override fun onSuccess(result: Sharer.Result) {

            }
        })
    }

    @Deprecated("Deprecated in Java")
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