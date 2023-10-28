package com.sudo248.sudoo.ui.activity.main.fragment.review

import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.visible
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.FragmentReviewBinding
import com.sudo248.sudoo.domain.entity.discovery.ProductInfo
import com.sudo248.sudoo.domain.entity.discovery.UpsertReview
import com.sudo248.sudoo.ui.activity.main.MainViewModel
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import com.sudo248.sudoo.ui.models.comment.RatingDescription
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import com.sudo248.sudoo.ui.util.FileUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment : BaseFragment<FragmentReviewBinding, ReviewViewModel>(), ViewController {
    override val viewModel: ReviewViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args: ReviewFragmentArgs by navArgs()
    override fun initView() {
        viewModel.setViewController(this)
        setProduct(args.review.productInfo)
        setupRating()
    }

    private fun setupRating() {
        binding.rating.setOnRatingBarChangeListener { _, value, b ->
            val ratingDescription = RatingDescription.from(value)
            binding.txtRatingDescription.apply {
                setText(ratingDescription.description)
                setTextColor(ContextCompat.getColor(requireContext(), ratingDescription.color))
            }
        }
    }

    override fun observer() {
        mainViewModel.imageUri.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.groupImageCommentProduct.visible()
                loadImage(binding.imgCommentProduct, it)
            } else {
                binding.groupImageCommentProduct.gone()
            }
        }
    }

    private fun setProduct(productInfo: ProductInfo) {
        binding.apply {
            loadImage(imgProduct, productInfo.images.first())
            txtNameProduct.text = productInfo.name
            txtProductBrand.text = getString(R.string.product_brand, productInfo.brand)
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

    override fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun getPathImageFromUri(uri: Uri): String {
        return FileUtils.getPathFromUri(requireContext(), uri)
    }

    override fun getUpsertReview(): UpsertReview {
        return UpsertReview(
            reviewId = args.review.reviewId,
            rate = binding.rating.rating,
            comment = binding.txtComment.text.toString()
        )
    }
}