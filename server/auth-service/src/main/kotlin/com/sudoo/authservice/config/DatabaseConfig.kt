package com.sudoo.authservice.config

import com.sudoo.domain.config.converter.InstantToLocalDateConverter
import com.sudoo.domain.config.converter.InstantToLocalDateTimeConverter
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.MySqlDialect
import com.sudoo.domain.config.converter.LocalDateTimeToInstantConverter
import com.sudoo.domain.config.converter.LocalDateToInstantConverter
import org.springframework.context.annotation.Bean

@Configuration
class DatabaseConfig {

    @Bean
    fun customConversion(): R2dbcCustomConversions {
        return R2dbcCustomConversions.of(
            MySqlDialect.INSTANCE,
            mutableListOf(
                LocalDateTimeToInstantConverter,
                InstantToLocalDateTimeConverter,
                LocalDateToInstantConverter,
                InstantToLocalDateConverter
            )
        )
    }
}