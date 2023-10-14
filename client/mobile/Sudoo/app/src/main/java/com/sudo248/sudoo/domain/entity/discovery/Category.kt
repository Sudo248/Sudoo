package com.sudo248.sudoo.domain.entity.discovery

import com.sudo248.base_android.base.ItemDiff


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:43 - 12/03/2023
 */
data class Category(
    val categoryId: String,
    val name: String,
    val image: String,
    var products: MutableList<ProductInfo>? = null,
) : java.io.Serializable, ItemDiff {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other is Category && this == other
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is Category && categoryId == other.categoryId
    }

}