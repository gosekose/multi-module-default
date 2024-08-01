package com.module.extension

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.toInstantKST(): Instant {
    return this.atZone(ZoneId.of("Asia/Seoul")).toInstant()
}

fun Instant.toKSTLocalDateTime(): LocalDateTime {
    return this.atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime()
}
