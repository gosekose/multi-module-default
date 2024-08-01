package com.module.application.cache

interface CacheManagementPort {
    fun <T> get(
        key: String,
        type: Class<T>,
    ): T?

    fun <T> set(
        key: String,
        value: T,
        durationMillis: Long,
    )
}
