package com.module.member.model

import com.module.domain.BaseDomainModel

/**
 * id: 식별자
 * memberId: 회원 ID
 * password: 해시된 패스워드
 * salt: 솔트
 */
data class MemberCredentials(
    override val id: Long = 0L,
    val memberId: Long,
    val password: String,
    val salt: String,
) : BaseDomainModel()
