package com.module.member.port.outport

import com.module.member.model.Member

interface MemberSavePort {
    fun save(member: Member): Member
}
