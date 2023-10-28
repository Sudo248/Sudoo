package com.sudo248.sudoo.ui.models.review

enum class ReviewListTab {
    NOT_YET_REVIEW,
    REVIEWED;

    val isReviewed
        get() = this == REVIEWED
}