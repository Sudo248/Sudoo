package com.sudoo.cartservice.repository

import com.sudoo.cartservice.repository.entity.Cart
import com.sudoo.cartservice.repository.entity.CartProduct
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
interface CartRepository: CoroutineCrudRepository<Cart, String> {
    suspend fun findCartByUserIdAndStatus(userId:String,status:String):  Flow<Cart>
    suspend fun countByUserIdAndStatus(userId: String,status: String):Int
}