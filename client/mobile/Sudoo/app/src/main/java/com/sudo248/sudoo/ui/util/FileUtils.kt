package com.sudo248.sudoo.ui.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream

object FileUtils {
    fun getPathFromUri(context: Context, uri: Uri): String {
        return getFileFromUri(context, uri).absolutePath
    }

    @SuppressLint("Recycle")
    fun getFileFromUri(context: Context, uri: Uri): File {
        try {
            var nameImage = "Unknow.png"
            context.contentResolver.query(uri, null, null, null)?.let { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                cursor.moveToFirst()
                nameImage = cursor.getString(nameIndex)
                cursor.close()
            }
            context.contentResolver.openInputStream(uri)?.let { inputStream ->
                val outputFile = File("${context.cacheDir}/$nameImage")
                copyStreamToFile(inputStream, outputFile)
                return outputFile
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw FileNotFoundException()
    }

    private fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }
}