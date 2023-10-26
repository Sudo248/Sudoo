package com.sudo248.sudoo.ui.activity.main.fragment.comment

import android.content.res.ColorStateList
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.visible
import com.sudo248.base_android.utils.ColorUtils
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.FragmentCommentBinding
import com.sudo248.sudoo.domain.entity.discovery.UpsertComment
import com.sudo248.sudoo.ui.activity.main.MainViewModel
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import com.sudo248.sudoo.ui.models.comment.RatingDescription
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import com.sudo248.sudoo.ui.util.FileUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment : BaseFragment<FragmentCommentBinding, CommentViewModel>(), ViewController {
    override val viewModel: CommentViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args: CommentFragmentArgs by navArgs()
    override fun initView() {
        viewModel.setViewController(this)
        viewModel.getProduct(args.productId)
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
        viewModel.productInfo.observe(viewLifecycleOwner) {
            binding.apply {
                loadImage(imgProduct, it.images.first())
                txtNameProduct.text = it.name
                txtProductBrand.text = getString(R.string.product_brand, it.brand)
            }
        }

        mainViewModel.imageUri.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.groupImageCommentProduct.visible()
                loadImage(binding.imgCommentProduct, it)
            } else {
                binding.groupImageCommentProduct.gone()
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

    override fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun getPathImageFromUri(uri: Uri): String {
        return FileUtils.getPathFromUri(requireContext(), uri)
    }

    override fun getUpsertComment(): UpsertComment {
        return UpsertComment(
            productId = args.productId,
            rate = binding.rating.rating,
            isLike = false,
            comment = binding.txtComment.text.toString()
        )
    }
}