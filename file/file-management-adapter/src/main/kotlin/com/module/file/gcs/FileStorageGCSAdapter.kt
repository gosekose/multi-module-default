//package com.module.file.gcs
//
//import com.module.domain.model.FileMetadata
//import com.module.domain.model.PresignedUrl
//import com.module.domain.port.outport.FileStoragePort
//import com.google.cloud.storage.BlobInfo
//import com.google.cloud.storage.HttpMethod
//import com.google.cloud.storage.Storage
//import org.springframework.boot.context.properties.EnableConfigurationProperties
//import java.util.concurrent.TimeUnit
//
//@EnableConfigurationProperties(GcsProperties::class)
//class FileStorageGCSAdapter(
//    private val storage: Storage,
//    private val gcsProperties: GcsProperties,
//) : FileStoragePort {
//    override fun generateFileUploadPresignedUrl(
//        fileId: Long,
//        fileKey: String,
//        durationMillis: Long,
//    ): PresignedUrl {
//        val blobInfo = BlobInfo.newBuilder(gcsProperties.bucket, fileKey).build()
//
//        val url =
//            storage.signUrl(
//                blobInfo,
//                durationMillis,
//                TimeUnit.MILLISECONDS,
//                Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
//                Storage.SignUrlOption.withV4Signature(),
//            )
//
//        return PresignedUrl(
//            url = url.toString(),
//            fileId = fileId,
//            fileKey = fileKey,
//            vendor = FileMetadata.Vendor.GCS,
//            actualUrl = generateActualUrl(fileKey),
//        )
//    }
//
//    private fun generateActualUrl(fileKey: String): String {
//        return "https://${gcsProperties.bucket}.storage.googleapis.com/$fileKey"
//    }
//}