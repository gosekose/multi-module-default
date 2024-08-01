package com.module.domain.exception

class ApplicationBusinessException : RuntimeException {
    val businessErrorCause: BusinessErrorCause
    val loggingMessage: String

    constructor(
        businessErrorCause: BusinessErrorCause,
    ) : super(businessErrorCause.message) {
        this.businessErrorCause = businessErrorCause
        this.loggingMessage = businessErrorCause.message
    }

    constructor(
        businessErrorCause: BusinessErrorCause,
        loggingMessage: String,
    ) : super(businessErrorCause.message) {
        this.businessErrorCause = businessErrorCause
        this.loggingMessage = loggingMessage
    }
}
