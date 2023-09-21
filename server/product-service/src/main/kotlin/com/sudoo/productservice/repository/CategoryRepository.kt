package com.sudoo.productservice.repository

import com.sudoo.productservice.model.Category
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : CoroutineCrudRepository<Category, String> {

}