package com.module.persistence.repository

import com.module.persistence.entity.MemberCredentialsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberCredentialsJpaRepository : JpaRepository<MemberCredentialsEntity, Long> {
    fun findByMemberId(memberId: Long): MemberCredentialsEntity?
}
