package com.module.domain.port.outport

import com.module.domain.model.Token

interface TokenPort {
    fun create(
        subject: String,
        subjectId: Long,
    ): Token

    fun parse(token: String): String
}
