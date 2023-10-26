package com.sudo248.sudoo.domain.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.sudoo.domain.entity.discovery.Category
import com.sudo248.sudoo.domain.entity.discovery.Comment
import com.sudo248.sudoo.domain.entity.discovery.CommentList
import com.sudo248.sudoo.domain.entity.discovery.Offset
import com.sudo248.sudoo.domain.entity.discovery.Product
import com.sudo248.sudoo.domain.entity.discovery.ProductInfo
import com.sudo248.sudoo.domain.entity.discovery.ProductList
import com.sudo248.sudoo.domain.entity.discovery.Supplier
import com.sudo248.sudoo.domain.entity.discovery.UpsertComment

interface DiscoveryRepository {
    suspend fun getCategories(): DataState<List<Category>, Exception>

    suspend fun getCategoryById(categoryId: String): DataState<Category, Exception>

    suspend fun getProductDetail(productId: String): DataState<Product, Exception>

    suspend fun getProductInfo(productId: String): DataState<ProductInfo, Exception>

    suspend fun searchProductByName(productName: String): DataState<ProductList, Exception>

    suspend fun getProductListByCategoryId(
        categoryId: String,
        offset: Offset
    ): DataState<ProductList, Exception>

    suspend fun getProductList(
        categoryId: String = "",
        name: String = "",
        offset: Offset
    ): DataState<ProductList, Exception>

    suspend fun getSupplierDetail(supplierId: String): DataState<Supplier, Exception>

    suspend fun getRecommendProductList(offset: Offset): DataState<ProductList, Exception>

    suspend fun getCommentsOfProduct(
        productId: String,
        offset: Offset
    ): DataState<CommentList, Exception>

    suspend fun upsertComment(upsertComment: UpsertComment): DataState<Comment, Exception>

    suspend fun deleteComment(commentId: String): DataState<Unit, Exception>
}