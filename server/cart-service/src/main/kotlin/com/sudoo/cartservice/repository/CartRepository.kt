package com.sudoo.cartservice.repository

import com.sudoo.cartservice.repository.entity.Cart
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface CartRepository: CoroutineCrudRepository<Cart, String> {
    fun findByUserIdAndStatus(@Param("user_id")userId: String, status: String): Flow<Cart>
}