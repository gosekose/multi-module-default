package com.module.api.security

import com.module.api.response.CustomResponse
import com.module.domain.exception.BusinessErrorCause
import com.module.extension.toJson
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus

object FilterErrorResponseWrapper {
    fun wrap(response: HttpServletResponse): HttpServletResponse {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(
            CustomResponse.ErrorResponse.of(
                status = HttpStatus.UNAUTHORIZED,
                message = BusinessErrorCause.UNAUTHORIZED.message,
            ).toJson()
        )
        return response
    }
}