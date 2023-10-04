package com.sudoo.cartservice.repository

import com.sudoo.cartservice.repository.entity.Cart
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface CartRepository {

    fun findByUserIdAndStatus(@Param("user_id")userId: String, status: String): Cart
}