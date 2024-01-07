package com.sudoo.domain.config.converter

import com.sudoo.domain.common.Constants
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId


@ReadingConverter
object LocalDateTimeToInstantConverter : Converter<LocalDateTime, Instant> {
    override fun convert(source: LocalDateTime): Instant? {
        return source.atZone(ZoneId.of(Constants.zoneId)).toInstant()
    }
}

@WritingConverter
object InstantToLocalDateTimeConverter : Converter<Instant, LocalDateTime> {
    override fun convert(source: Instant): LocalDateTime {
        return LocalDateTime.ofInstant(source, ZoneId.of(Constants.zoneId))
    }
}

@ReadingConverter
object LocalDateToInstantConverter : Converter<LocalDate, Instant> {
    override fun convert(source: LocalDate): Instant? {
        return source.atStartOfDay(ZoneId.of(Constants.zoneId)).toInstant()
    }
}

@WritingConverter
object InstantToLocalDateConverter : Converter<Instant, LocalDate> {
    override fun convert(source: Instant): LocalDate {
        return LocalDate.ofInstant(source, ZoneId.of(Constants.zoneId))
    }
}