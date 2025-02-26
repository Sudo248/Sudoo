package com.sudo248.sudoo.data.di

import com.sudo248.sudoo.data.repository.*
import com.sudo248.sudoo.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 00:46 - 05/03/2023
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindDiscoveryRepository(discoveryRepository: DiscoveryRepositoryImpl): DiscoveryRepository

    @Binds
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindPaymentRepository(paymentRepository: PaymentRepositoryImpl): PaymentRepository

    @Binds
    abstract fun bindImageRepository(imageRepository: StorageRepositoryImpl): StorageRepository

    @Binds
    abstract fun bindCartRepository(cartRepository: CartRepositoryImpl): CartRepository

    @Binds
    abstract fun bindOrderRepository(orderRepository: OrderRepositoryImpl): OrderRepository

    @Binds
    abstract fun bindPromotionRepository(promotionRepository: PromotionRepositoryImpl): PromotionRepository

    @Binds
    abstract fun bindChatRepository(chatRepository: ChatRepositoryImpl): ChatRepository

}