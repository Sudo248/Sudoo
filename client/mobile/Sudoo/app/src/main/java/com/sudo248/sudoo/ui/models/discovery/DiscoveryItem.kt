package com.sudo248.sudoo.ui.models.discovery

import com.sudo248.sudoo.domain.entity.discovery.Category
import com.sudo248.sudoo.domain.entity.discovery.CategoryInfo
import com.sudo248.sudoo.domain.entity.discovery.ProductInfo

sealed class DiscoveryItem(val id: String) {
    open val canAdded: Boolean = true

    data class BannerItem(
        val images: List<String>
    ) : DiscoveryItem("banner_item")

    data class CategoriesItem(
        val title: String,
        val categories: List<Category>
    ) : DiscoveryItem("categories_item")

    data class RecommendProductItem(
        val title: String,
        val products: List<ProductInfo>
    ) : DiscoveryItem("recommend_product_item")

    data class CategoriesItemTab(
        val title:String,
        val categories: List<CategoryInfo>
    ) : DiscoveryItem("categories_item_tab")

    data class ProductItem(
        val index: Int,
        val product: ProductInfo,
        val categoryId: String,
    ) : DiscoveryItem("product_item_${categoryId}.${product.productId}")
}