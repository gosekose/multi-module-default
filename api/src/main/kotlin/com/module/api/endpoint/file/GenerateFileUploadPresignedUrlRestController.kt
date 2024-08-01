package com.module.api.endpoint.file

import com.module.api.response.CustomResponse
import com.module.api.response.objectbody.PresignedUrlMetadataHttpResponseBody
import com.module.api.security.filter.XMemberIdHeader.X_MEMBER_ID
import com.module.domain.port.inport.GenerateFileUploadPresignedUrlUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GenerateFileUploadPresignedUrlRestController(
    private val generateFileUploadPresignedUrlUseCase: GenerateFileUploadPresignedUrlUseCase,
) {
    @GetMapping("/api/v1/files/presignedUrl/upload")
    fun generatePresignedUrl(
        @RequestHeader(X_MEMBER_ID) memberId: Long,
        @RequestParam fileName: String,
    ): ResponseEntity<CustomResponse.SuccessResponse<PresignedUrlMetadataHttpResponseBody>> {
        val responseBody = generateFileUploadPresignedUrlUseCase
            .generateFileUploadPresignedUrl(
                GenerateFileUploadPresignedUrlUseCase.Command(
                    memberId = memberId,
                    fileName = fileName,
                ),
            ).let(PresignedUrlMetadataHttpResponseBody::from)

        return ResponseEntity(
            CustomResponse.SuccessResponse.of(
                status = HttpStatus.OK,
                data = responseBody,
            ),
            HttpStatus.OK
        )
    }
}