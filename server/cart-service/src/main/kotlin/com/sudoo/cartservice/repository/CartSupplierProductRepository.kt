package com.sudoo.cartservice.repository

import org.springframework.stereotype.Repository

@Repository
interface CartSupplierProductRepository {
    fun countByCart_UserIdAndCart_Status(userId: String?, status: String?): Int?
}