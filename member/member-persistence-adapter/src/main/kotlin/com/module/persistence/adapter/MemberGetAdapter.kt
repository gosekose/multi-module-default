package com.module.persistence.adapter

import com.module.domain.exception.BusinessErrorCause
import com.module.domain.validation.returnIfNotNull
import com.module.member.model.Member
import com.module.member.model.PhoneNumber
import com.module.member.port.outport.MemberGetPort
import com.module.persistence.repository.MemberJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class MemberGetAdapter(
    private val memberJpaRepository: MemberJpaRepository,
) : MemberGetPort {
    override fun getByMemberId(memberId: Long): Member {
        return returnIfNotNull(
            memberJpaRepository.findByIdOrNull(memberId),
            BusinessErrorCause.MEMBER_NOT_FOUND,
        ).toModel()
    }

    override fun getOrNullByPhoneNumber(phoneNumber: PhoneNumber): Member? {
        return memberJpaRepository.findByPhoneNumber(phoneNumber.number)?.toModel()
    }

    override fun getByPhoneNumber(phoneNumber: PhoneNumber): Member {
        return returnIfNotNull(
            memberJpaRepository.findByPhoneNumber(phoneNumber.number),
            BusinessErrorCause.PHONE_NUMBER_NOT_FOUND,
        ).toModel()
    }
}
