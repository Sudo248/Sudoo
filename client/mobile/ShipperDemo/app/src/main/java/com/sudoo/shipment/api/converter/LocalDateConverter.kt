package com.sudoo.shipment.api.converter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter : JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {
        return LocalDate.parse(json?.asJsonPrimitive?.asString)
    }

    override fun serialize(
        src: LocalDate?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive((src ?: LocalDate.now()).format(DateTimeFormatter.ISO_LOCAL_DATE))
    }
}
