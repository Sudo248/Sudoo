package com.sudo248.sudoo.data.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.sudo248.base_android.data.api.ApiService
import com.sudo248.base_android.data.api.api
import com.sudo248.sudoo.data.api.auth.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.cart.CartService
import com.sudo248.sudoo.data.api.chat.ChatService
import com.sudo248.sudoo.data.api.discovery.DiscoveryService
import com.sudo248.sudoo.data.api.image.ImageService
import com.sudo248.sudoo.data.api.invoice.InvoiceService
import com.sudo248.sudoo.data.api.notification.NotificationService
import com.sudo248.sudoo.data.api.payment.PaymentService
import com.sudo248.sudoo.data.api.promotion.PromotionService
import com.sudo248.sudoo.data.api.user.UserService
import com.sudo248.sudoo.data.converter.LocalDateTimeConverter
import java.time.LocalDateTime


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 10:18 - 23/02/2023
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideAuthService(): AuthService = ApiService()

    @Singleton
    @Provides
    fun provideDiscoveryService(): DiscoveryService = api {
        converterFactory = GsonConverterFactory.create(
            GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeConverter()).create()
        )
    }

    @Singleton
    @Provides
    fun provideUserService(): UserService = api {
        converterFactory = GsonConverterFactory.create(
            GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create()
        )
    }

    @Singleton
    @Provides
    fun provideImageService(): ImageService = ApiService()

    @Singleton
    @Provides
    fun providePaymentService(): PaymentService = ApiService()

    @Singleton
    @Provides
    fun provideCartService(): CartService = ApiService()

    @Singleton
    @Provides
    fun providePromotionService(): PromotionService = ApiService()

    @Singleton
    @Provides
    fun provideInvoiceService(): InvoiceService = ApiService()

    @Singleton
    @Provides
    fun provideNotificationService(): NotificationService = ApiService()

    @Singleton
    @Provides
    fun provideChatService(): ChatService = ApiService()

    @Singleton
    @Provides
    fun provideIODispatcher() = Dispatchers.IO

}