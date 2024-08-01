package com.module.domain.validation

import com.module.domain.exception.ApplicationBusinessException
import com.module.domain.exception.BusinessErrorCause
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun requireBusiness(
    condition: Boolean,
    errorCause: BusinessErrorCause,
    loggingMessage: (BusinessErrorCause) -> String = { "" },
) {
    contract {
        returns() implies condition
    }

    if (!condition) {
        throw ApplicationBusinessException(errorCause, loggingMessage(errorCause))
    }
}

@OptIn(ExperimentalContracts::class)
fun <T : Any> returnIfNotNull(
    value: T?,
    errorCause: BusinessErrorCause,
): T {
    contract {
        returns() implies (value != null)
    }

    if (value == null) {
        throw ApplicationBusinessException(errorCause)
    } else {
        return value
    }
}
