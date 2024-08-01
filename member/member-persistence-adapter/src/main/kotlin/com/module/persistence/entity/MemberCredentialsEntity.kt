package com.module.persistence.entity

import com.module.member.model.MemberCredentials
import com.module.persistence.base.BaseJpaEntity
import com.module.persistence.base.applyWithDomainModel
import com.module.persistence.base.applyWithEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "member_credentials",
    indexes = [
        Index(name = "idx_member_credentials__member_id", columnList = "member_id"),
    ],
)
class MemberCredentialsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "member_id", nullable = false, columnDefinition = "BIGINT")
    val memberId: Long,
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    val password: String,
    @Column(name = "salt", nullable = false, columnDefinition = "TEXT")
    val salt: String,
) : BaseJpaEntity() {
    fun toModel(): MemberCredentials {
        return MemberCredentials(
            id = id,
            memberId = memberId,
            password = password,
            salt = salt,
        ).applyWithEntity(this)
    }

    companion object {
        @JvmStatic
        fun from(memberCredentials: MemberCredentials): MemberCredentialsEntity {
            return with(memberCredentials) {
                MemberCredentialsEntity(
                    id = id,
                    memberId = memberId,
                    password = password,
                    salt = salt,
                )
            }.applyWithDomainModel(memberCredentials)
        }
    }
}
