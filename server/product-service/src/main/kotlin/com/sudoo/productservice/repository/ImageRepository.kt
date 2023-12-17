package com.sudoo.productservice.repository

import com.sudoo.productservice.model.Image
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : CoroutineCrudRepository<Image, String> {
    fun getAllByOwnerId(ownerId: String): Flow<Image>
}