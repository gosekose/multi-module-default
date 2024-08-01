package com.module.application.lock

interface DistributedLockManager {
    fun <T> lock(
        key: String,
        block: () -> T,
    ): T
}
