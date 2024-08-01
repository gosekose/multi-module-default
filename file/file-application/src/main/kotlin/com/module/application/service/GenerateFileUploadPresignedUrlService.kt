package com.module.application.service

import com.module.domain.model.FileMetadata
import com.module.domain.model.PresignedUrl
import com.module.domain.port.inport.GenerateFileUploadPresignedUrlUseCase
import com.module.domain.port.inport.GenerateFileUploadPresignedUrlUseCase.Command
import com.module.domain.port.outport.FileMetadataSavePort
import com.module.domain.port.outport.FileStoragePort
import com.module.extension.UUIDGenerator
import org.springframework.stereotype.Service

@Service
class GenerateFileUploadPresignedUrlService(
    private val fileMetadataSavePort: FileMetadataSavePort,
    private val fileStoragePort: FileStoragePort,
) : GenerateFileUploadPresignedUrlUseCase {
    override fun generateFileUploadPresignedUrl(command: Command): PresignedUrl {
        val (memberId, fileName) = command
        val fileMetadata = fileMetadataSavePort.save(
            FileMetadata.of(
                memberId = memberId,
                originalFileName = fileName,
                fileKey = UUIDGenerator.generate(),
            )
        )

        val presignedUrl =
            fileStoragePort.generateFileUploadPresignedUrl(
                fileId = fileMetadata.id,
                fileKey = fileMetadata.fileKey,
                durationMillis = DURATION_MILLIS,
            )

        fileMetadataSavePort.save(
            fileMetadata.update(
                FileMetadata.VendorMetadata(
                    vendor = presignedUrl.vendor,
                    actualUrl = presignedUrl.actualUrl
                )
            )
        )

        return presignedUrl
    }

    companion object {
        private const val DURATION_MILLIS = 1000 * 60 * 10L // 10분
    }
}