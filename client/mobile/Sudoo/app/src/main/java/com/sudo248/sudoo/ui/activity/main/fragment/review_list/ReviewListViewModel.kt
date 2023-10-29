package com.sudo248.sudoo.ui.activity.main.fragment.review_list

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.base_android.navigation.ResultCallback
import com.sudo248.sudoo.domain.entity.discovery.Offset
import com.sudo248.sudoo.domain.entity.discovery.Review
import com.sudo248.sudoo.domain.repository.DiscoveryRepository
import com.sudo248.sudoo.ui.activity.main.adapter.ReviewAdapter
import com.sudo248.sudoo.ui.base.LoadMoreListener
import com.sudo248.sudoo.ui.models.review.ReviewListTab
import com.sudo248.sudoo.ui.util.BundleKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewListViewModel @Inject constructor(
    private val discoveryRepository: DiscoveryRepository
) : BaseViewModel<NavDirections>() {

    var error: SingleEvent<String?> = SingleEvent(null)

    private val _isRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRefresh: LiveData<Boolean> = _isRefresh

    private var reviewedNextOffset: Offset = Offset()
    private var notYetReviewNextOffset: Offset = Offset()

    private var currentTab: ReviewListTab = ReviewListTab.NOT_YET_REVIEW

    val reviewedAdapter: ReviewAdapter = ReviewAdapter(
        ReviewListTab.REVIEWED,
        onLoadMore = object : LoadMoreListener {
            override fun onLoadMore() {
                getReviewList(ReviewListTab.REVIEWED, isLoadMore = true)
            }
        },
    )

    val notYetReviewedAdapter: ReviewAdapter = ReviewAdapter(
        ReviewListTab.NOT_YET_REVIEW,
        onLoadMore = object : LoadMoreListener {
            override fun onLoadMore() {
                getReviewList(ReviewListTab.NOT_YET_REVIEW, isLoadMore = true)
            }
        },
        onClickReview = ::onClickReview
    )

    init {
        refresh()
    }

    fun onClickTabItem(tab: ReviewListTab) {
        if (tab.isReviewed && reviewedAdapter.isEmpty) {
            getReviewList(tab)
        }
        currentTab = tab
    }

    fun refresh() {
        _isRefresh.value = true
        getReviewList(currentTab)
    }

    fun refreshAll() {
        _isRefresh.value = true
        getReviewList(ReviewListTab.REVIEWED)
        getReviewList(ReviewListTab.NOT_YET_REVIEW)
    }

    fun getReviewList(tab: ReviewListTab, isLoadMore: Boolean = false) = launch {
        if (!isLoadMore) {
            if (tab.isReviewed) {
                reviewedNextOffset.reset()
            } else {
                notYetReviewNextOffset.reset()
            }
        }
        discoveryRepository.getReviews(
            isReviewed = tab.isReviewed,
            if (tab.isReviewed) reviewedNextOffset else notYetReviewNextOffset
        )
            .onSuccess {
                if (tab.isReviewed) {
                    if (it.reviews.size < reviewedNextOffset.limit) {
                        reviewedAdapter.isLastPage(true)
                    } else {
                        reviewedNextOffset.offset = it.pagination.offset
                    }
                    reviewedAdapter.submitData(it.reviews, extend = isLoadMore)
                } else {
                    if (it.reviews.size < notYetReviewNextOffset.limit) {
                        notYetReviewedAdapter.isLastPage(true)
                    } else {
                        notYetReviewNextOffset.offset = it.pagination.offset
                    }
                    notYetReviewedAdapter.submitData(it.reviews, extend = isLoadMore)
                }
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
        _isRefresh.value = false
    }

    private fun onClickReview(review: Review) {
        navigator.navigateForResult(
            ReviewListFragmentDirections.actionReviewListFragmentToReviewFragment(
                review
            ),
            BundleKeys.REVIEW_FRAGMENT_KEY,
            object : ResultCallback {
                override fun onResult(key: String, data: Bundle?) {
                    if (key == BundleKeys.REVIEW_FRAGMENT_KEY) {
                        if (data?.getBoolean(BundleKeys.NEED_RELOAD) == true){
                            refreshAll()
                        }
                    }
                }
            })
    }

    fun back() {
        navigator.back()
    }
}