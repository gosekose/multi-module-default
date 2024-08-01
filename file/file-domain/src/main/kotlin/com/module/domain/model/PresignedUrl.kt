package com.module.domain.model

data class PresignedUrl(
    val url: String,
    val fileId: Long,
    val fileKey: String,
    val vendor: FileMetadata.Vendor,
    val actualUrl: String,
)