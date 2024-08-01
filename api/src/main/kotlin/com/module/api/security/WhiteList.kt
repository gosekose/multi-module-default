package com.module.api.security

object WhiteList {
    val WHITE_LIST =
        listOf(
            "/error",
            "/api/**/login",
            "/api/**/logout",
            "/api/**/signup",
            "/api/**/health",
            "/swagger.html/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/favicon.ico",
            "/actuator/**",
            "/monitor/**",
            "/grafana/**",
        )
}
