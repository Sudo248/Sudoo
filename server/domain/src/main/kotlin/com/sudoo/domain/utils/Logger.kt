package com.sudoo.domain.utils

import org.slf4j.LoggerFactory

object Logger {
    private val logger = LoggerFactory.getLogger(Logger::class.java)

    fun error(throwable: Throwable, message: String? = null) {
        logger.error(message ?: "[ERROR]", throwable)
    }
}