package com.sudoo.productservice.repository

import com.sudoo.productservice.model.Image
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : CoroutineCrudRepository<Image, String> {
}