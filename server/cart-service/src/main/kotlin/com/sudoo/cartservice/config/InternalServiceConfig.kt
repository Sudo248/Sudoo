package com.sudoo.productservice.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class InternalServiceConfig {

    @Bean
    @Primary
    @Qualifier("cart-service")
    fun userWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://cart-service/api/v1/cart/")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()
    }

}