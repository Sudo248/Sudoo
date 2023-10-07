package com.sudoo.cartservice.repository

import com.sudoo.cartservice.repository.entity.Cart
import com.sudoo.cartservice.repository.entity.CartSupplierProduct
import com.sudoo.cartservice.repository.entity.SupplierProductKey
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CartSupplierProductRepository: CoroutineCrudRepository<CartSupplierProduct, SupplierProductKey> {
    fun countByCart_UserIdAndCart_Status(userId: String?, status: String?): Flow<Int>
}