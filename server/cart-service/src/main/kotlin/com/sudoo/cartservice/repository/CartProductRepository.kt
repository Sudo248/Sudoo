package com.sudoo.cartservice.repository

import com.sudoo.cartservice.repository.entity.Cart
import com.sudoo.cartservice.repository.entity.CartProduct
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CartProductRepository : CoroutineCrudRepository<CartProduct, String> {
    suspend fun findCartProductByCartIdAndProductId(cartId: String, productId: String): CartProduct
    suspend fun findCartProductByCartId(cartId: String): Flow<CartProduct>
    suspend fun deleteCartProductByCartIdAndProductId(cartId: String, productId: String): Int

}