package com.module.file.gcs

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.cloud.gcp")
data class GcsProperties(
    val projectId: String,
    val credentials: Credential,
    val bucket: String,
) {
    data class Credential(
        val jsonPath: String,
    )
}