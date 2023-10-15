package com.sudo248.sudoo.ui.util

import android.annotation.SuppressLint
import com.sudo248.base_android.utils.DateUtils
import com.sudo248.sudoo.domain.entity.user.Gender
import com.sudo248.sudoo.domain.ktx.format
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object Utils {
    private val locale = Locale("vi", "VN")
    private const val dateFormat = "dd/MM/yyyy"

    fun format(value: Double, digit: Int = 2): String {
        return value.format(digit)
    }

    fun formatVnCurrency(value: Double): String {
        return NumberFormat.getCurrencyInstance(locale).format(value)
    }

    fun formatDistance(distance: Double, unit: String): String {
        return if (unit == "m") {
            "${distance.toInt()}$unit"
        } else {
            "${distance.format(2)}$unit"
        }
    }

    fun formatDuration(duration: Double, unit: String): String {
        return if (unit == "ngày" || unit == "giờ") {
            "${duration.format(2)} $unit"
        } else {
            "${duration.toInt()} $unit"
        }
    }

    fun formatShortSold(value: Double): String {
        return if (value >= 100) {
            " . 100+"
        } else {
            " . ${value.toInt()}+"
        }
    }

    fun formatSold(value: Int): String {
        return "Đã bán: ${
            if (value >= 1000) {
                "${format(value / 1000.0, digit = 1)}k"
            } else {
                "$value"
            }
        }"
    }

    fun formatHideInfo(value: String, numberPlainText: Int = 0): String {
        return buildString {
            val _numberPlainText =
                if (numberPlainText < 0) 0 else if (numberPlainText > value.length) value.length else numberPlainText
            val hideLength = value.length - _numberPlainText
            repeat(hideLength) {
                this.append('*')
            }
            append(value.substring(hideLength))
        }
    }

    fun formatDob(date: Date): String {
        return DateUtils.format(date.time, dateFormat, locale)
    }

    fun parseDob(date: String): Date {
        if (date.isEmpty()) return Date()
        return DateUtils.parse(date, dateFormat) ?: Date()
    }
}