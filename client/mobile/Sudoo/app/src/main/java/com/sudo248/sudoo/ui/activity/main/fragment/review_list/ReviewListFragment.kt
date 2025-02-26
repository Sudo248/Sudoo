package com.sudo248.sudoo.ui.activity.main.fragment.review_list

import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.visible
import com.sudo248.sudoo.databinding.FragmentReviewListBinding
import com.sudo248.sudoo.ui.models.review.ReviewListTab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewListFragment : BaseFragment<FragmentReviewListBinding, ReviewListViewModel>() {
    override val viewModel: ReviewListViewModel by viewModels()
    private val arg: ReviewListFragmentArgs by navArgs()
    override fun initView() {
        binding.viewModel = viewModel
        binding.rcvReviewed.adapter = viewModel.reviewedAdapter
        binding.rcvNotYetReview.adapter = viewModel.notYetReviewedAdapter
        viewModel.isAfterPaymentArg = arg.isAfterPayment
        binding.refresh.setOnRefreshListener {
            viewModel.refresh()
        }
        setupTabLayout()
    }

    override fun observer() {
        viewModel.isRefresh.observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = it
        }
    }

    private fun setupTabLayout() {
        binding.tabReview.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    if (it.position == 0) {
                        setNotYetReviewTab()
                        viewModel.onClickTabItem(ReviewListTab.NOT_YET_REVIEW)
                    } else {
                        setReviewedTab()
                        viewModel.onClickTabItem(ReviewListTab.REVIEWED)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setNotYetReviewTab() {
        binding.apply {
            rcvReviewed.gone()
            rcvNotYetReview.visible()
        }
    }

    private fun setReviewedTab() {
        binding.apply {
            rcvReviewed.visible()
            rcvNotYetReview.gone()
        }
    }

}