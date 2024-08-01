package com.module.persistence.adapter

import com.module.domain.exception.BusinessErrorCause
import com.module.domain.validation.returnIfNotNull
import com.module.member.model.MemberCredentials
import com.module.member.port.outport.MemberCredentialGetPort
import com.module.persistence.repository.MemberCredentialsJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class MemberCredentialsGetAdapter(
    private val memberCredentialsJpaRepository: MemberCredentialsJpaRepository,
) : MemberCredentialGetPort {
    override fun getByMemberId(memberId: Long): MemberCredentials {
        return returnIfNotNull(
            memberCredentialsJpaRepository.findByMemberId(memberId),
            BusinessErrorCause.MEMBER_NOT_FOUND,
        ).toModel()
    }
}
