package com.sudoo.apigateway.config

import com.sudoo.apigateway.filter.ApiFilter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
class ApiGatewayConfig(
    @Lazy private val apiFilter: ApiFilter
) {

    fun routes(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes().run {
            SudooServices.values().forEach { service ->
                route(service.id) { predicate ->
                    predicate.path(*service.pattern).filters {
                        filter -> filter.filter(apiFilter)
                    }
                        .uri(service.uri)
                }
            }
            build()
        }
    }

}