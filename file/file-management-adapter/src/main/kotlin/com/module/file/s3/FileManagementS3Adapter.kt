package com.module.file.s3

import com.module.domain.model.FileMetadata
import com.module.domain.model.PresignedUrl
import com.module.domain.port.outport.FileStoragePort
import org.springframework.boot.context.properties.EnableConfigurationProperties
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

@EnableConfigurationProperties(AwsS3Properties::class)
class FileManagementS3Adapter(
    private val s3Presigner: S3Presigner,
    private val awsS3Properties: AwsS3Properties,
) : FileStoragePort {
    override fun generateFileUploadPresignedUrl(
        fileId: Long,
        fileKey: String,
        durationMillis: Long,
    ): PresignedUrl {
        val putObjectRequest =
            PutObjectRequest.builder()
                .bucket(awsS3Properties.s3.bucket)
                .key(fileKey)
                .build()

        val presignRequest =
            PutObjectPresignRequest.builder()
                .putObjectRequest(putObjectRequest)
                .signatureDuration(Duration.ofMillis(durationMillis))
                .build()

        val presignedPutObjectRequest = s3Presigner.presignPutObject(presignRequest)
        return PresignedUrl(
            url = presignedPutObjectRequest.url().toString(),
            fileId = fileId,
            fileKey = fileKey,
            vendor = FileMetadata.Vendor.S3,
            actualUrl = generateActualUrl(fileKey),
        )
    }

    private fun generateActualUrl(fileKey: String): String {
        return "https://${awsS3Properties.s3.bucket}.s3.${awsS3Properties.region}.amazonaws.com/$fileKey"
    }
}