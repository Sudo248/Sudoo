package com.sudoo.productservice.repository

import com.sudoo.productservice.model.ProductExtras
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductExtrasRepository : CoroutineCrudRepository<ProductExtras, String> {
}