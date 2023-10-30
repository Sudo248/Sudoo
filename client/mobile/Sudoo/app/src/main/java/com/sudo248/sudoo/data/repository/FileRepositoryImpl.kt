package com.sudo248.sudoo.data.repository

import android.content.Context
import android.net.Uri
import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.sudoo.data.api.file.FileService
import com.sudo248.sudoo.data.ktx.data
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.domain.repository.FileRepository
import com.sudo248.sudoo.ui.util.FileUtils
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FileRepositoryImpl @Inject constructor(
    private val fileService: FileService,
    private val ioDispatcher: CoroutineDispatcher
) : FileRepository {

    override suspend fun uploadImage(context: Context, uri: Uri): DataState<String, Exception> = stateOn(ioDispatcher) {
        val imageFile = FileUtils.getFileFromUri(context, uri)
        val requestBody = imageFile.asRequestBody(context.contentResolver.getType(uri)?.toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
        val response = handleResponse(fileService.uploadImage(filePart))
        if (response.isSuccess) {
            response.data().imageUrl
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun uploadImage(pathImage: String): DataState<String, Exception> = stateOn(ioDispatcher){
        val imageFile = File(pathImage)
        val typeImage = imageFile.name.substringAfterLast('.')
        val requestBody = imageFile.asRequestBody("image/$typeImage".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
        val response = handleResponse(fileService.uploadImage(filePart))
        if (response.isSuccess) {
            response.data().imageUrl
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getImageResource(context: Context, source: String): DataState<Uri, Exception> = stateOn(ioDispatcher) {
        TODO("Not yet implemented")
    }
}