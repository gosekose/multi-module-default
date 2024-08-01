package com.module.application.aspect

import com.module.application.annotation.CacheableAnnotation
import com.module.application.cache.CacheManagementPort
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Component

@Aspect
@Component
class CacheableAspect(
    private val cacheManagementPort: CacheManagementPort,
) {
    private val parser = SpelExpressionParser()

    @Around("@annotation(cacheableAnnotation)")
    fun around(
        joinPoint: ProceedingJoinPoint,
        cacheableAnnotation: CacheableAnnotation,
    ): Any? {
        val key = "${cacheableAnnotation.cacheName}:${generateKey(joinPoint, cacheableAnnotation)}"

        val cachedValue = cacheManagementPort.get(key, cacheableAnnotation.clazz.java)
        if (cachedValue != null) {
            return cachedValue
        }

        return joinPoint.proceed().apply {
            cacheManagementPort.set(key, this, cacheableAnnotation.durationMillis)
        }
    }

    private fun generateKey(
        joinPoint: ProceedingJoinPoint,
        cacheableAnnotation: CacheableAnnotation,
    ): String {
        val method =
            joinPoint.signature.declaringType.getDeclaredMethod(
                joinPoint.signature.name,
                *joinPoint.args.map { it::class.java }.toTypedArray(),
            )
        val context = StandardEvaluationContext()
        method.parameters.forEachIndexed { index, parameter ->
            context.setVariable(parameter.name, joinPoint.args[index])
        }
        val expression = parser.parseExpression(cacheableAnnotation.key)
        return requireNotNull(expression.getValue(context)).toString()
    }
}
