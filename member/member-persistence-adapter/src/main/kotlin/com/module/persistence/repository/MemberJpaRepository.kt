package com.module.persistence.repository

import com.module.persistence.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<MemberEntity, Long> {
    fun findByPhoneNumber(phoneNumber: String): MemberEntity?
}
