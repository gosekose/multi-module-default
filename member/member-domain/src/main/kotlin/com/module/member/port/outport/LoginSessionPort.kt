package com.module.member.port.outport

import com.module.domain.model.Token

interface LoginSessionPort {
    fun getOrNull(userId: String): Token?

    fun save(
        userId: String,
        token: Token,
    )

    fun delete(userId: String)
}
