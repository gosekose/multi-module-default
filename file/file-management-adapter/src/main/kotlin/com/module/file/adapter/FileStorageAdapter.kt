package com.module.file.adapter

import com.module.domain.model.PresignedUrl
import com.module.domain.port.outport.FileStoragePort
import com.module.file.gcs.FileStorageGCSAdapter
import com.module.file.s3.FileManagementS3Adapter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class FileStorageAdapter(
    @Qualifier("fileManagementS3Adapter") private val s3Adapter: FileManagementS3Adapter,
    @Qualifier("fileManagementGCSAdapter") private val gcsAdapter: FileStorageGCSAdapter,
    circuitBreakerFactory: CircuitBreakerFactory<*, *>,
) : FileStoragePort {
    private val circuitBreaker = circuitBreakerFactory.create("s3CircuitBreaker")

    override fun generateFileUploadPresignedUrl(
        fileId: Long,
        fileKey: String,
        durationMillis: Long,
    ): PresignedUrl {
        return circuitBreaker.run({
            s3Adapter.generateFileUploadPresignedUrl(
                fileId = fileId,
                fileKey = fileKey,
                durationMillis = durationMillis
            )
        }, { _ ->
            gcsAdapter.generateFileUploadPresignedUrl(
                fileId = fileId,
                fileKey = fileKey,
                durationMillis = durationMillis
            )
        })
    }
}