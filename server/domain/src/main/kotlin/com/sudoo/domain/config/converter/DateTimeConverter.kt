package com.sudoo.domain.config.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId


private val zoneId = ZoneId.of("Asia/Ho_Chi_Minh")

@ReadingConverter
object LocalDateTimeToInstantConverter : Converter<LocalDateTime, Instant> {
    override fun convert(source: LocalDateTime): Instant? {
        return source.atZone(zoneId).toInstant()
    }
}

@WritingConverter
object InstantToLocalDateTimeConverter : Converter<Instant, LocalDateTime> {
    override fun convert(source: Instant): LocalDateTime {
        return LocalDateTime.ofInstant(source, zoneId)
    }
}

@ReadingConverter
object LocalDateToInstantConverter : Converter<LocalDate, Instant> {
    override fun convert(source: LocalDate): Instant? {
        return source.atStartOfDay(zoneId).toInstant()
    }
}

@WritingConverter
object InstantToLocalDateConverter : Converter<Instant, LocalDate> {
    override fun convert(source: Instant): LocalDate {
        return LocalDate.ofInstant(source, zoneId)
    }
}