package com.module.extension

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val GSON: Gson =
    GsonBuilder()
        .registerTypeAdapter(Instant::class.java, InstantAdapter().nullSafe())
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter().nullSafe())
        .serializeNulls()
        .create()

class InstantAdapter : TypeAdapter<Instant>() {
    override fun write(
        writer: JsonWriter,
        value: Instant?,
    ) {
        value?.let {
            writer.value(DateTimeFormatter.ISO_INSTANT.format(it))
        }
    }

    override fun read(reader: JsonReader): Instant {
        return Instant.parse(reader.nextString())
    }
}

fun Any.toJson(): String = GSON.toJson(this)

fun <T> String.parseJson(type: Class<T>): T = GSON.fromJson(this, type)

class LocalDateTimeAdapter : TypeAdapter<LocalDateTime>() {
    override fun write(
        writer: JsonWriter,
        value: LocalDateTime?,
    ) {
        value?.let {
            writer.value(it.format(format))
        }
    }

    override fun read(reader: JsonReader): LocalDateTime {
        return LocalDateTime.parse(reader.nextString(), format)
    }

    companion object {
        private const val PATTERN_LOCAL_DATE_TIME = "yyyy-MM-dd HH:mm:ss"
        private val format: DateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_LOCAL_DATE_TIME)
    }
}
