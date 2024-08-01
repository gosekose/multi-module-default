package com.module.member.port.inport

import com.module.domain.model.Token
import com.module.member.model.PhoneNumber

interface MemberLoginUseCase {
    fun login(command: LoginCommand): Token

    data class LoginCommand(
        val phoneNumber: PhoneNumber,
        val password: String,
    )
}
