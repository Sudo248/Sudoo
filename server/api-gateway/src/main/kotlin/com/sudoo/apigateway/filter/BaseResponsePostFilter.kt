package com.sudoo.apigateway.filter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class BaseResponsePostFilter : GlobalFilter, Ordered {
    val objectMapper = ObjectMapper()
    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        print("Pre BaseResponsePostFilter")
        return chain.filter(exchange)/*.then(
            Mono.fromRunnable {
                val responseBodyInputStream = exchange.response.bufferFactory().allocateBuffer().asInputStream()
                try {
                    print("Sudoo: ")
                    val body = objectMapper.readValue(responseBodyInputStream, String::class.java)
                    print(body)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        )*/
    }

    override fun getOrder(): Int = -1

}