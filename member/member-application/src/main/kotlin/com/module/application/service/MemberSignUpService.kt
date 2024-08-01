package com.module.application.service

import com.module.domain.exception.BusinessErrorCause
import com.module.domain.validation.requireBusiness
import com.module.extension.UUIDGenerator
import com.module.member.model.Member
import com.module.member.model.MemberCredentials
import com.module.member.port.inport.MemberSignUpUseCase
import com.module.member.port.outport.MemberCredentialsSavePort
import com.module.member.port.outport.MemberGetPort
import com.module.member.port.outport.MemberSavePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberSignUpService(
    private val memberGetPort: MemberGetPort,
    private val memberSavePort: MemberSavePort,
    private val memberCredentialsSavePort: MemberCredentialsSavePort,
) : MemberSignUpUseCase {
    override fun signUp(command: MemberSignUpUseCase.SignUpCommand) {
        requireBusiness(
            memberGetPort.getOrNullByPhoneNumber(command.phoneNumber) == null,
            BusinessErrorCause.PHONE_NUMBER_ALREADY_EXISTS,
        )

        val savedMember =
            memberSavePort.save(
                Member(
                    userId = UUIDGenerator.generate(),
                    phoneNumber = command.phoneNumber,
                ),
            )

        val salt = PasswordUtils.generateSalt()
        val hashedPassword = PasswordUtils.hashPassword(command.password, salt)

        memberCredentialsSavePort.save(
            MemberCredentials(
                memberId = savedMember.id,
                password = hashedPassword,
                salt = salt,
            ),
        )
    }
}
