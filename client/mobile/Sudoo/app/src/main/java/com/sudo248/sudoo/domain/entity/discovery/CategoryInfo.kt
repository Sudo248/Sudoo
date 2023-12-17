package com.sudo248.sudoo.domain.entity.discovery

import com.sudo248.base_android.base.ItemDiff

data class CategoryInfo(
    val categoryId: String = "",
    val name: String,
    val image: String,
) : ItemDiff, java.io.Serializable {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other == this
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other == this
    }
}