package com.module.api.response

import org.springframework.http.HttpStatus

sealed class CustomResponse<T> {
    data class Meta(
        val code: Int,
        val message: String,
    ) {
        companion object {
            @JvmStatic
            fun of(
                status: HttpStatus,
                message: String,
            ): Meta {
                return Meta(
                    code = status.value(),
                    message = message,
                )
            }
        }
    }

    data class SuccessResponse<T>(
        val meta: Meta,
        val data: T,
    ) : CustomResponse<T>() {
        companion object {
            @JvmStatic
            fun <T> of(
                status: HttpStatus,
                data: T,
            ): SuccessResponse<T> {
                return SuccessResponse(
                    meta = Meta.of(status, status.reasonPhrase),
                    data = data,
                )
            }
        }
    }

    data class ErrorResponse(
        val meta: Meta,
        val data: Any? = null,
    ) : CustomResponse<Nothing>() {
        companion object {
            @JvmStatic
            fun of(
                status: HttpStatus,
                message: String,
                data: Any? = null,
            ): ErrorResponse {
                return ErrorResponse(
                    meta = Meta.of(status, message),
                    data = data,
                )
            }
        }
    }
}
