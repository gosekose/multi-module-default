package com.module.domain.port.outport

import com.module.domain.model.PresignedUrl

interface FileStoragePort {
    fun generateFileUploadPresignedUrl(
        fileId: Long,
        fileKey: String,
        durationMillis: Long,
    ): PresignedUrl
}