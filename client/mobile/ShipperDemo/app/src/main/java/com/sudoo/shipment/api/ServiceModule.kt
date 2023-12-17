package com.sudoo.shipment.api

import com.google.gson.GsonBuilder
import com.sudo248.base_android.data.api.ApiService
import com.sudo248.base_android.data.api.api
import com.sudoo.shipment.api.converter.LocalDateConverter
import com.sudoo.shipment.api.converter.LocalDateTimeConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideAuthService(): AuthService = ApiService()

    @Singleton
    @Provides
    fun provideOrderService(): OrderService = api {
        converterFactory = GsonConverterFactory.create(
            GsonBuilder()
                .registerTypeAdapter(LocalDate::class.java, LocalDateConverter())
                .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeConverter())
                .create()
        )
    }
}