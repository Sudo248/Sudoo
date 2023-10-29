package com.sudo248.sudoo.ui.activity.main.fragment.product_detail

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.ShareLinkContent
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.invisible
import com.sudo248.base_android.ktx.visible
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.FragmentProductDetailBinding
import com.sudo248.sudoo.domain.entity.discovery.Product
import com.sudo248.sudoo.domain.entity.discovery.SupplierInfo
import com.sudo248.sudoo.ui.activity.main.MainActivity
import com.sudo248.sudoo.ui.activity.main.MainViewModel
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import com.sudo248.sudoo.ui.ktx.toSlideModel
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
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args: ProductDetailFragmentArgs by navArgs()

    override val enableStateScreen: Boolean = true

    private val callbackManager = CallbackManager.Factory.create()

    private var badge: BadgeDrawable? = null

    override fun initView() {
        binding.viewModel = viewModel
        viewModel.viewController = this
        viewModel.parentViewModel = mainViewModel
        binding.rcvComment.adapter = viewModel.commentAdapter
        viewModel.getProduct(args.productId)
        binding.refreshProductDetail.setOnRefreshListener {
            viewModel.refresh()
        }
        createBadgeCart()
        viewModel.countItemInCart()
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
            setImageSlideshow(product.images)
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
            txtSku.text = getString(R.string.sku_label, product.sku)
            setRating(product.rate)
            txtNumberSold.text = Utils.formatSold(product.soldAmount)
            txtDescription.text = product.description
            txtBrand.text = product.brand
            if (this@ProductDetailFragment.viewModel.canAddToCart()) {
                binding.txtBuyNow.text = getString(R.string.add_to_cart)
            } else {
                binding.txtBuyNow.text = getString(R.string.contact)
            }
        }
    }

    private fun performSupplier(supplier: SupplierInfo) {
        binding.apply {
            loadImage(imgAvatarSupplier, supplier.avatar)
            txtNameSupplier.text = supplier.name
            txtSupplierLocation.text = supplier.address.provinceName
            txtSupplierRate.text = Utils.format(supplier.rate.toDouble(), digit = 1)
        }
    }

    private fun setRating(rating: Float) {
        binding.rating.rating = rating
        binding.txtNumberStart.text = Utils.format(rating.toDouble(), digit = 1)
    }

    private fun setImageSlideshow(images: List<String>) {
        binding.slideshowImageProduct.apply {
            setImageList(images.map { it.toSlideModel() }, ScaleTypes.CENTER_INSIDE)
        }
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
    @androidx.annotation.OptIn(com.google.android.material.badge.ExperimentalBadgeUtils::class)
    private fun createBadgeCart() {
        badge = BadgeDrawable.create(requireContext()).apply {
            isVisible = false
            BadgeUtils.attachBadgeDrawable(this, binding.imgCart, binding.frameCart)
            badgeGravity = BadgeDrawable.TOP_END
            verticalOffset = 3
            backgroundColor = ContextCompat.getColor(requireContext(), R.color.primaryColor)
            badgeTextColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
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
        if (amount > 0) {
            badge?.isVisible = true
            badge?.number = amount
        } else {
            badge?.isVisible = false
        }
    }

    override fun openContact(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun toast(id: Int, duration: Int) {
        Toast.makeText(requireContext(), getString(id), duration).show()
    }
}