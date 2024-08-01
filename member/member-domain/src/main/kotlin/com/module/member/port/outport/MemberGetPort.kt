package com.module.member.port.outport

import com.module.member.model.Member
import com.module.member.model.PhoneNumber

interface MemberGetPort {
    fun getByMemberId(memberId: Long): Member

    fun getOrNullByPhoneNumber(phoneNumber: PhoneNumber): Member?

    fun getByPhoneNumber(phoneNumber: PhoneNumber): Member
}
