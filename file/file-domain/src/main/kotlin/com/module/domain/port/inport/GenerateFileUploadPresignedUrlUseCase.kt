package com.module.domain.port.inport

import com.module.domain.model.PresignedUrl

interface GenerateFileUploadPresignedUrlUseCase {
    fun generateFileUploadPresignedUrl(command: Command): PresignedUrl

    data class Command(
        val memberId: Long,
        val fileName: String,
    )
}