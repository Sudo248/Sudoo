package com.sudoo.productservice.repository

import com.sudoo.productservice.model.Product
import com.sudoo.productservice.model.ProductInfo
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CoroutineCrudRepository<Product, String> {
    suspend fun getProductBySku(sku: String): Product?

    @Query(
        """
        SELECT 
        products.product_id, 
        products.sku, 
        products.name, 
        products.price,
        products.listed_price,
        products.amount,
        products.saleable,
        products.rate,
        products.discount,
        products.start_date_discount,
        products.end_date_discount
        FROM products
        WHERE products.product_id = :productId
        LIMIT 1;
    """
    )
    suspend fun getProductInfoById(@Param("productId") productId: String): ProductInfo

    @Query(
        """
        SELECT 
        products.product_id, 
        products.sku, 
        products.name, 
        products.price,
        products.listed_price,
        products.amount,
        products.saleable,
        products.rate,
        products.discount,
        products.start_date_discount,
        product.end_date_discount
        FROM products 
        WHERE products.name LIKE %:_name%
        LIMIT :_limit
        OFFSET :_offset
    """
    )
    fun searchProductInfoByName(@Param("_name") name: String): Flow<ProductInfo>

    @Query(
        """
        SELECT 
        products.product_id, 
        products.supplier_id,
        products.sku, 
        products.name, 
        products.price,
        products.listed_price,
        products.amount,
        products.saleable,
        products.rate,
        products.discount,
        products.start_date_discount,
        products.end_date_discount
        FROM products 
        LIMIT :_limit
        OFFSET :_offset
    """
    )
    fun getProductInfoWithOffset(@Param("_offset") offset: Int = 0, @Param("_limit") limit: Int = 0): Flow<ProductInfo>

    @Query(
        """
        SELECT categories_products.product_id 
        FROM categories_products 
        WHERE categories_products.category_id = :categoryId 
        LIMIT :_limit
        OFFSET :_offset;
    """
    )
    fun getProductIdByCategoryIdWithOffset(
        @Param("categoryId") categoryId: String,
        @Param("_offset") offset: Int = 0,
        @Param("_limit") limit: Int = 0
    ): Flow<String>

    suspend fun countBySupplierId(supplierId: String): Long

    @Query(
        """
        SELECT 
        products.product_id, 
        products.supplier_id,
        products.sku, 
        products.name, 
        products.price,
        products.listed_price,
        products.amount,
        products.saleable,
        products.rate,
        products.discount,
        products.start_date_discount,
        products.end_date_discount
        FROM products 
        WHERE products.supplier_id = :supplierId
        LIMIT :_limit
        OFFSET :_offset
    """
    )
    fun getProductInfoBySupplierIdWithOffset(
        @Param("supplierId") supplierId: String,
        @Param("_offset") offset: Int = 0,
        @Param("_limit") limit: Int = 0
    ): Flow<ProductInfo>

}