package com.module.persistence.adapter

import com.module.member.model.MemberCredentials
import com.module.member.port.outport.MemberCredentialsSavePort
import com.module.persistence.entity.MemberCredentialsEntity
import com.module.persistence.repository.MemberCredentialsJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class MemberCredentialsSaveAdapter(
    private val memberCredentialsJpaRepository: MemberCredentialsJpaRepository,
) : MemberCredentialsSavePort {
    override fun save(memberCredentials: MemberCredentials): MemberCredentials {
        return memberCredentialsJpaRepository.save(MemberCredentialsEntity.from(memberCredentials)).toModel()
    }
}
