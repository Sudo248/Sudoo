package com.sudoo.cartservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CartServiceApplication

fun main(args: Array<String>) {
    runApplication<CartServiceApplication>(*args)
}

