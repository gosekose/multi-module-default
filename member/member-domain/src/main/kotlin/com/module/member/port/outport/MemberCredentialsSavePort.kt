package com.module.member.port.outport

import com.module.member.model.MemberCredentials

interface MemberCredentialsSavePort {
    fun save(memberCredentials: MemberCredentials): MemberCredentials
}
