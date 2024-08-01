package com.module.domain.model

import com.module.domain.BaseDomainModel

data class FileMetadata(
    override val id: Long = 0L,
    val memberId: Long,
    val originalFileName: String,
    var vendor: VendorMetadata?,
    val fileKey: String,
) : BaseDomainModel() {

    data class VendorMetadata(
        val vendor: Vendor,
        val actualUrl: String,
    )

    fun update(vendorMetadata: VendorMetadata): FileMetadata {
        return this.apply {
            this.vendor = vendorMetadata
        }
    }

    enum class Vendor {
        S3,
        GCS,
    }

    companion object {
        @JvmStatic
        fun of(
            memberId: Long,
            originalFileName: String,
            fileKey: String,
        ): FileMetadata {
            return FileMetadata(
                memberId = memberId,
                originalFileName = originalFileName,
                fileKey = fileKey,
                vendor = null,
            )
        }
    }
}