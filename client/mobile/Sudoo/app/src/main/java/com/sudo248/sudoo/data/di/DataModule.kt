package com.sudo248.sudoo.data.di

import com.google.gson.GsonBuilder
import com.sudo248.base_android.data.api.ApiService
import com.sudo248.base_android.data.api.api
import com.sudo248.sudoo.data.api.auth.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.sudo248.sudoo.data.api.cart.CartService
import com.sudo248.sudoo.data.api.chat.ChatService
import com.sudo248.sudoo.data.api.discovery.DiscoveryService
import com.sudo248.sudoo.data.api.image.ImageService
import com.sudo248.sudoo.data.api.order.OrderService
import com.sudo248.sudoo.data.api.notification.NotificationService
import com.sudo248.sudoo.data.api.payment.PaymentService
import com.sudo248.sudoo.data.api.promotion.PromotionService
import com.sudo248.sudoo.data.api.user.UserService
import com.sudo248.sudoo.data.converter.LocalDateConverter
import com.sudo248.sudoo.data.converter.LocalDateTimeConverter
import java.time.LocalDate
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
            GsonBuilder().registerTypeAdapter(LocalDate::class.java, LocalDateConverter()).create()
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
    fun provideOrderService(): OrderService = ApiService()

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