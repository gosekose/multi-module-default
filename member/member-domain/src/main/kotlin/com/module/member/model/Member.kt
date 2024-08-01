package com.module.member.model

import com.module.domain.BaseDomainModel

/**
 * id: 식별자
 * phone: 휴대폰 번호
 */
data class Member(
    override val id: Long = 0L,
    val userId: String,
    val phoneNumber: PhoneNumber,
) : BaseDomainModel()
