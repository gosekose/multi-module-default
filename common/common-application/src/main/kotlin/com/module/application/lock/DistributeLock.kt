package com.module.application.lock

interface DistributeLock {
    fun tryLock(): Boolean

    fun unlock()
}
