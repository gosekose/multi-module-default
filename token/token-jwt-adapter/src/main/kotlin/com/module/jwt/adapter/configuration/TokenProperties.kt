package com.module.jwt.adapter.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource

@PropertySource("classpath:application-jwt.yml")
@ConfigurationProperties(prefix = "jwt")
data class TokenProperties(
    val secretKey: String,
    val expirationMillis: Long,
)
