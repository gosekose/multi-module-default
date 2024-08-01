package com.module.application.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedLockBeforeTransaction(
    val key: Array<String>,
    val prefix: DistributedLockPrefixKey,
    val separator: String = ":",
    val transactionalReadOnly: Boolean = false,
)
