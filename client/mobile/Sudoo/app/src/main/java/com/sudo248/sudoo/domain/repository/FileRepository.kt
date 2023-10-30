package com.sudo248.sudoo.domain.repository

import android.content.Context
import android.net.Uri
import com.sudo248.base_android.core.DataState

interface FileRepository {
    suspend fun uploadImage(context: Context, uri: Uri): DataState<String, Exception>
    suspend fun uploadImage(pathImage: String): DataState<String, Exception>

    suspend fun getImageResource(context: Context, source: String): DataState<Uri, Exception>
}