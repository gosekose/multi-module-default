package com.module.member.model

import com.module.domain.exception.BusinessErrorCause
import com.module.domain.validation.requireBusiness

data class PhoneNumber(
    val number: String,
) {
    init {
        requireBusiness(
            REGEX.matches(number),
            BusinessErrorCause.INVALID_PHONE_NUMBER_FORMAT,
        )
    }

    companion object {
        private val REGEX = Regex("^\\d{3}-\\d{4}-\\d{4}$")
    }
}
