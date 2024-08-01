package com.module.application.service

import com.module.domain.exception.BusinessErrorCause
import com.module.domain.model.Token
import com.module.domain.port.outport.TokenPort
import com.module.domain.validation.requireBusiness
import com.module.member.port.inport.MemberLoginUseCase
import com.module.member.port.outport.LoginSessionPort
import com.module.member.port.outport.MemberCredentialGetPort
import com.module.member.port.outport.MemberGetPort
import org.springframework.stereotype.Service

@Service
class MemberLoginService(
    private val tokenPort: TokenPort,
    private val memberGetPort: MemberGetPort,
    private val loginSessionPort: LoginSessionPort,
    private val memberCredentialGetPort: MemberCredentialGetPort,
) : MemberLoginUseCase {
    override fun login(command: MemberLoginUseCase.LoginCommand): Token {
        val (phoneNumber, password) = command
        val member = memberGetPort.getByPhoneNumber(phoneNumber)
        val memberCredentials = memberCredentialGetPort.getByMemberId(member.id)

        requireBusiness(
            PasswordUtils.hashPassword(password, memberCredentials.salt) == memberCredentials.password,
            BusinessErrorCause.INVALID_PASSWORD,
        )

        return tokenPort.create(
            subject = member.userId,
            subjectId = member.id,
        ).also { token ->
            loginSessionPort.save(member.userId, token)
        }
    }
}
