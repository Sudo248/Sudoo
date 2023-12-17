package com.sudo248.sudoo.ui.models.comment

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.sudo248.sudoo.R

enum class RatingDescription(@StringRes val description: Int, @ColorRes val color: Int) {
    ONE(R.string.bad, R.color.black),
    TWO(R.string.unsatisfied, R.color.black),
    THREE(R.string.normal, R.color.black),
    FOUR(R.string.satisfied, R.color.yellow_dark_20),
    FIVE(R.string.great, R.color.yellow_dark_20);

    companion object {
        fun from(value: Float): RatingDescription {
            return when {
                value <= 1.0f -> ONE
                value > 1.0f && value <= 2.0f -> TWO
                value > 2.0f && value <= 3.0f -> THREE
                value > 3.0f && value <= 4.0f -> FOUR
                else -> FIVE
            }
        }
    }
}