package com.module.member.port.outport

import com.module.member.model.MemberCredentials

interface MemberCredentialGetPort {
    fun getByMemberId(memberId: Long): MemberCredentials
}
