package com.sudo248.sudoo.data.repository

import android.content.Context
import android.net.Uri
import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.exception.ApiException
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.sudoo.data.api.storage.StorageService
import com.sudo248.sudoo.data.ktx.data
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.domain.repository.StorageRepository
import com.sudo248.sudoo.ui.util.FileUtils
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StorageRepositoryImpl @Inject constructor(
    private val storageService: StorageService,
    private val ioDispatcher: CoroutineDispatcher
) : StorageRepository {

    override suspend fun uploadImage(context: Context, uri: Uri): DataState<String, Exception> = stateOn(ioDispatcher) {
        val imageFile = FileUtils.getFileFromUri(context, uri)
        val requestBody = imageFile.asRequestBody(context.contentResolver.getType(uri)?.toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
        val response = handleResponse(storageService.uploadImage(filePart))
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
        val response = handleResponse(storageService.uploadImage(filePart))
        if (response.isSuccess) {
            response.data().imageUrl
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getArModelResource(parent: File, source: String): DataState<Uri, Exception> = stateOn(ioDispatcher) {
        File(parent, source)
        val file = File(parent, source)
//        if (file.length() <= 0) {
            val response = storageService.downloadFile(source)
            if (!response.isSuccessful || response.body() == null) {
                throw ApiException(response.code(), response.message())
            }
            var input: InputStream? = null
            try {
                input = response.body()!!.byteStream()
                FileOutputStream(file).use { output ->
                    val buffer = ByteArray(4 * 1024) // or other buffer size
                    var read: Int
                    while (input.read(buffer).also { read = it } != -1) {
                        output.write(buffer, 0, read)
                    }
                    output.flush()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
            finally {
                input?.close()
            }
//        }
        Uri.fromFile(file)
    }
}