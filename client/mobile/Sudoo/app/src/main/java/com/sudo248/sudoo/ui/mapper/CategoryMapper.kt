package com.sudo248.sudoo.ui.mapper

import androidx.databinding.ObservableField
import com.sudo248.sudoo.domain.entity.discovery.Category
import com.sudo248.sudoo.domain.entity.discovery.CategoryInfo
import com.sudo248.sudoo.ui.uimodel.CategoryUiModel


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 23:12 - 12/03/2023
 */

fun Category.toCategoryUi(): CategoryUiModel {
    return CategoryUiModel(
        categoryId = categoryId,
        name = name,
        imageUrl = imageUrl
    )
}

fun CategoryInfo.toCategory(): Category {
    return Category(
        categoryId = categoryId,
        name = name,
        imageUrl = image,
        products = listOf()
    )
}