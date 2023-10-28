package com.sudo248.sudoo.ui.activity.main.fragment.review_list

import com.sudo248.sudoo.ui.models.review.ReviewListTab

interface ViewController {
    fun onSelectedTab(tab: ReviewListTab)
}