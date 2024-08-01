package com.module.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

object UUIDGenerator {
    private const val LOCAL_DATE_TIME_FORMAT = "yyyyMMddHHmmssSSS"
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT)

    fun generate(): String {
        val currentTime = LocalDateTime.now().format(formatter)
        val uuid = UUID.randomUUID().toString()
        return "$currentTime-$uuid"
    }
}