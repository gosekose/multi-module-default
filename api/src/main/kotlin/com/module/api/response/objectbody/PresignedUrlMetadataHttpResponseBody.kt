package com.module.api.response.objectbody

import com.module.domain.model.PresignedUrl

data class PresignedUrlMetadataHttpResponseBody(
    val fileId: Long,
    val url: String,
) {
    companion object {
        @JvmStatic
        fun from(presignedUrl: PresignedUrl): PresignedUrlMetadataHttpResponseBody {
            return with(presignedUrl) {
                PresignedUrlMetadataHttpResponseBody(
                    fileId = fileId,
                    url = url,
                )
            }
        }
    }
}