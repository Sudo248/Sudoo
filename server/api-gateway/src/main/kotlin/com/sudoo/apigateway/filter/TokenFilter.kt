//package com.sudoo.apigateway.filter
//
//import com.sudoo.apigateway.utils.TokenUtils
//import com.sudoo.domain.common.Constants
//import com.sudoo.domain.common.SudooError
//import com.sudoo.domain.exception.ApiException
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.reactive.awaitFirstOrNull
//import kotlinx.coroutines.reactor.mono
//import org.slf4j.LoggerFactory
//import org.springframework.cloud.gateway.filter.GatewayFilter
//import org.springframework.cloud.gateway.filter.GatewayFilterChain
//import org.springframework.core.annotation.Order
//import org.springframework.http.HttpStatus
//import org.springframework.stereotype.Component
//import org.springframework.web.server.ResponseStatusException
//import org.springframework.web.server.ServerWebExchange
//import reactor.core.publisher.Mono
//
//@Component
//@Order(2)
//class TokenFilter(
//    private val tokenUtils: TokenUtils
//) : GatewayFilter {
//
//    private val log = LoggerFactory.getLogger(TokenFilter::class.java)
//
//    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
//        var token = exchange.request.headers.getFirst(Constants.AUTHORIZATION)
//        if (!token.isNullOrEmpty()) {
//            return mono(Dispatchers.Unconfined) {
//                if (token!!.startsWith(Constants.TOKEN_TYPE)) {
//                    token = token!!.replace(Constants.TOKEN_TYPE + " ", "")
//                } else {
//                    throw ResponseStatusException(HttpStatus.UNAUTHORIZED, SudooError.MUST_CONTAIN_TOKEN_TYPE.message)
//                }
//                try {
//                    if (tokenUtils.validateToken(token)) {
//                        val userId = tokenUtils.getUserIdFromToken(token)
//                        val request = exchange.request.mutate().header(Constants.HEADER_USER_ID, userId).build()
//                        val newExchange = exchange.mutate().request(request).build()
//                        chain.filter(newExchange).awaitFirstOrNull()
//                    }
//                } catch (e: ApiException) {
//                    log.error("Authentication failed: " + e.message)
//
////                    throw ResponseStatusException(HttpStatus.UNAUTHORIZED, e.message)
//                }
//            }
//        }
//        return chain.filter(exchange)
//    }
//
//}