package com.module.application.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CacheableAnnotation(
    val cacheName: String,
    val key: String,
    val clazz: KClass<*>,
    val durationMillis: Long,
)
