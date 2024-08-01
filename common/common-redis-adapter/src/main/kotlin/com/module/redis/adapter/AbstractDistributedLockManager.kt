package com.module.redis.adapter

import com.module.application.lock.DistributeLock
import com.module.application.lock.DistributedLockManager
import com.module.domain.exception.ApplicationBusinessException
import com.module.domain.exception.BusinessErrorCause
import org.springframework.transaction.support.TransactionSynchronizationManager

abstract class AbstractDistributedLockManager : DistributedLockManager {
    abstract fun generateLock(key: String): DistributeLock

    override fun <T> lock(
        key: String,
        block: () -> T,
    ): T {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            throw ApplicationBusinessException(BusinessErrorCause.INTERNAL_SERVER_ERROR)
        }

        val lock = generateLock(key)

        if (!lock.tryLock()) {
            throw ApplicationBusinessException(BusinessErrorCause.DISTRIBUTE_LOCK_TRY_FAILED)
        }

        return try {
            block()
        } finally {
            lock.unlock()
        }
    }
}
