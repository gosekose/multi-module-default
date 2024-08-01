package com.module.persistence.adapter

import com.module.member.model.Member
import com.module.member.port.outport.MemberSavePort
import com.module.persistence.entity.MemberEntity
import com.module.persistence.repository.MemberJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class MemberSaveAdapter(
    private val memberJpaRepository: MemberJpaRepository,
) : MemberSavePort {
    override fun save(member: Member): Member {
        return memberJpaRepository.save(MemberEntity.from(member)).toModel()
    }
}
