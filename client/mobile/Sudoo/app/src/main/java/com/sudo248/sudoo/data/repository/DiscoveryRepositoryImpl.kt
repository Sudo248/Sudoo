package com.sudo248.sudoo.data.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.sudoo.data.api.discovery.DiscoveryService
import com.sudo248.sudoo.data.ktx.data
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.data.mapper.toCategory
import com.sudo248.sudoo.data.mapper.toComment
import com.sudo248.sudoo.data.mapper.toCommentList
import com.sudo248.sudoo.data.mapper.toProduct
import com.sudo248.sudoo.data.mapper.toProductInfo
import com.sudo248.sudoo.data.mapper.toProductList
import com.sudo248.sudoo.data.mapper.toReview
import com.sudo248.sudoo.data.mapper.toReviewList
import com.sudo248.sudoo.data.mapper.toSupplier
import com.sudo248.sudoo.data.mapper.toUpsertCommentDto
import com.sudo248.sudoo.domain.entity.discovery.Category
import com.sudo248.sudoo.domain.entity.discovery.CommentList
import com.sudo248.sudoo.domain.entity.discovery.Offset
import com.sudo248.sudoo.domain.entity.discovery.Pagination
import com.sudo248.sudoo.domain.entity.discovery.Product
import com.sudo248.sudoo.domain.entity.discovery.ProductInfo
import com.sudo248.sudoo.domain.entity.discovery.ProductList
import com.sudo248.sudoo.domain.entity.discovery.Review
import com.sudo248.sudoo.domain.entity.discovery.ReviewList
import com.sudo248.sudoo.domain.entity.discovery.Supplier
import com.sudo248.sudoo.domain.entity.discovery.UpsertReview
import com.sudo248.sudoo.domain.repository.DiscoveryRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiscoveryRepositoryImpl @Inject constructor(
    private val discoveryService: DiscoveryService,
    private val ioDispatcher: CoroutineDispatcher
) : DiscoveryRepository {

    override suspend fun getCategories(): DataState<List<Category>, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(discoveryService.getCategories())
            if (response.isSuccess) {
                response.data().map { it.toCategory() }
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun getCategoryById(categoryId: String): DataState<Category, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(discoveryService.getCategoryById(categoryId))
            if (response.isSuccess) {
                response.data().toCategory()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun getProductDetail(productId: String): DataState<Product, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(discoveryService.getProductDetail(productId))
            if (response.isSuccess) {
                response.data().toProduct()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun getProductInfo(productId: String): DataState<ProductInfo, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(discoveryService.getProductInfo(productId))
            if (response.isSuccess) {
                response.data().toProductInfo()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun searchProductByName(productName: String): DataState<ProductList, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(discoveryService.searchProductByName(productName))
            if (response.isSuccess) {
                response.data().toProductList()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun getProductListByCategoryId(
        categoryId: String,
        offset: Offset
    ): DataState<ProductList, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(
            discoveryService.getProductListByCategoryId(
                categoryId,
                offset = offset.offset,
                limit = offset.limit
            )
        )
        if (response.isSuccess) {
            response.data().toProductList()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getProductList(
        categoryId: String,
        name: String,
        offset: Offset
    ): DataState<ProductList, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(
            discoveryService.getProductList(
                categoryId = categoryId,
                name = name,
                offset = offset.offset,
                limit = offset.limit
            )
        )
        if (response.isSuccess) {
//            response.data().toProductList()
            mockProductList(response.data().toProductList())
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getSupplierDetail(
        supplierId: String
    ): DataState<Supplier, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(discoveryService.getSupplierDetail(supplierId))
        if (response.isSuccess) {
            response.data().toSupplier()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getRecommendProductList(offset: Offset): DataState<ProductList, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(
                discoveryService.getRecommendProductList(
                    offset = offset.offset,
                    limit = offset.limit
                )
            )
            if (response.isSuccess) {
                response.data().toProductList()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun getCommentsOfProduct(
        productId: String,
        offset: Offset
    ): DataState<CommentList, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(
            discoveryService.getCommentsOfProduct(
                productId = productId,
                offset = offset.offset,
                limit = offset.limit
            )
        )
        if (response.isSuccess) {
            response.data().toCommentList()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getReviews(
        isReviewed: Boolean,
        offset: Offset
    ): DataState<ReviewList, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(
            discoveryService.getReviews(
                isReviewed = isReviewed,
                offset = offset.offset,
                limit = offset.limit,
            )
        )
        if (response.isSuccess) {
            response.data().toReviewList()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun upsertReview(upsertReview: UpsertReview): DataState<Review, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(
                discoveryService.upsertReview(
                    upsertReview.toUpsertCommentDto()
                )
            )
            if (response.isSuccess) {
                response.data().toReview()
            } else {
                throw response.error().errorBody()
            }
        }

    override suspend fun deleteComment(commentId: String): DataState<Unit, Exception> =
        stateOn(ioDispatcher) {
            val response = handleResponse(
                discoveryService.deleteComment(
                    commentId
                )
            )
            if (response.isSuccess) {
                DataState.Success(Unit)
            } else {
                throw response.error().errorBody()
            }
        }

    private fun mockProductList(raw: ProductList): ProductList {
        val products = raw.products.toMutableList()
        products.addAll(mockProductInfo(9))
        return ProductList(
            products = products,
            pagination = Pagination(
                offset = raw.pagination.offset + 9,
                total = raw.pagination.total + 9
            )
        )
    }

    private fun mockProductInfo(size: Int) = List(size) {
        ProductInfo(
            productId = "product $it",
            name = "Product $it",
            sku = "$it",
            images = listOf("product_default.png"),
            price = 10000.0,
            listedPrice = 10000.0,
            amount = 10,
            rate = 5.0f,
            discount = 0,
            startDateDiscount = null,
            endDateDiscount = null,
            saleable = true,
            brand = "brand $it"
        )
    }
}