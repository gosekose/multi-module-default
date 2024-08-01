package com.module.member.port.inport

import com.module.member.model.PhoneNumber

interface MemberSignUpUseCase {
    fun signUp(command: SignUpCommand)

    data class SignUpCommand(
        val phoneNumber: PhoneNumber,
        val password: String,
    )
}
