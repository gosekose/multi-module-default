package com.module.api.advice

import com.module.api.response.CustomResponse
import com.module.domain.exception.ApplicationBusinessException
import com.module.domain.exception.BusinessErrorCause
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestValueException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler(ApplicationBusinessException::class)
    fun handleException(businessException: ApplicationBusinessException): ResponseEntity<CustomResponse.ErrorResponse> {
        return when (businessException.businessErrorCause) {
            // 400
            BusinessErrorCause.INVALID_PASSWORD,
            BusinessErrorCause.INVALID_PHONE_NUMBER_FORMAT,
            BusinessErrorCause.SIZE_NOT_AVAILABLE
            ->
                buildErrorResponse(
                    businessException,
                    HttpStatus.BAD_REQUEST,
                )

            // 401
            BusinessErrorCause.UNAUTHORIZED ->
                buildErrorResponse(
                    businessException,
                    HttpStatus.UNAUTHORIZED,
                )

            // 404
            BusinessErrorCause.MEMBER_NOT_FOUND,
            BusinessErrorCause.PHONE_NUMBER_NOT_FOUND,
            BusinessErrorCause.PRODUCT_NOT_FOUND,
            BusinessErrorCause.FILE_NOT_FOUND,
            ->
                buildErrorResponse(
                    businessException,
                    HttpStatus.NOT_FOUND,
                )

            // 409
            BusinessErrorCause.PHONE_NUMBER_ALREADY_EXISTS ->
                buildErrorResponse(
                    businessException,
                    HttpStatus.CONFLICT,
                )

            // 423
            BusinessErrorCause.DISTRIBUTE_LOCK_TRY_FAILED ->
                buildErrorResponse(
                    businessException,
                    HttpStatus.LOCKED,
                )

            // 500
            BusinessErrorCause.INTERNAL_SERVER_ERROR ->
                buildErrorResponse(
                    businessException,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                )
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(exception: NoHandlerFoundException): CustomResponse.ErrorResponse {
        val message = "Handler not found [${exception.httpMethod} ${exception.requestURL}]"
        return CustomResponse.ErrorResponse.of(
            status = HttpStatus.NOT_FOUND,
            message = message,
        )
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InsufficientAuthenticationException::class)
    fun handleAuthenticationException(exception: InsufficientAuthenticationException): CustomResponse.ErrorResponse {
        return CustomResponse.ErrorResponse.of(
            status = HttpStatus.UNAUTHORIZED,
            message = BusinessErrorCause.UNAUTHORIZED.message,
        )
    }

    @ExceptionHandler(
        IllegalArgumentException::class,
        NullPointerException::class,
        MethodArgumentNotValidException::class,
        MissingServletRequestParameterException::class,
        MissingRequestValueException::class,
        BindException::class,
        HttpMessageNotReadableException::class,
    )
    fun handleBadRequest(exception: Exception): CustomResponse.ErrorResponse {
        return CustomResponse.ErrorResponse.of(
            status = HttpStatus.BAD_REQUEST,
            message = "파라미터가 올바르지 않습니다.",
        )
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleOtherException(exception: Exception): CustomResponse.ErrorResponse {
        return CustomResponse.ErrorResponse.of(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            message = BusinessErrorCause.INTERNAL_SERVER_ERROR.message,
        )
    }

    private fun buildErrorResponse(
        applicationBusinessException: ApplicationBusinessException,
        httpStatus: HttpStatus,
    ): ResponseEntity<CustomResponse.ErrorResponse> {
        return ResponseEntity.status(httpStatus).body(
            CustomResponse.ErrorResponse.of(
                status = httpStatus,
                message = applicationBusinessException.businessErrorCause.message,
            ),
        )
    }
}
