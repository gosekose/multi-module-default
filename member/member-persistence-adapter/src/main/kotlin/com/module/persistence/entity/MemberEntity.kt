package com.module.persistence.entity

import com.module.member.model.Member
import com.module.member.model.PhoneNumber
import com.module.persistence.base.BaseJpaEntity
import com.module.persistence.base.SoftDelete
import com.module.persistence.base.applyWithDomainModel
import com.module.persistence.base.applyWithEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "members",
    indexes = [
        Index(name = "idx_members__phone_number", columnList = "phone_number"),
    ],
)
class MemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "user_id", nullable = false, columnDefinition = "VARCHAR(255)")
    val userId: String,
    @Column(name = "phone_number", nullable = false, columnDefinition = "VARCHAR(30)")
    val phoneNumber: String,
    @Enumerated(value = EnumType.STRING)
    @Column(name = "soft_delete_status", columnDefinition = "VARCHAR(16)", nullable = false)
    var softDelete: SoftDelete,
) : BaseJpaEntity() {
    fun toModel(): Member {
        return Member(
            id = id,
            userId = userId,
            phoneNumber = PhoneNumber(phoneNumber),
        ).applyWithEntity(this)
    }

    companion object {
        @JvmStatic
        fun from(member: Member): MemberEntity {
            return with(member) {
                MemberEntity(
                    id = id,
                    phoneNumber = phoneNumber.number,
                    userId = userId,
                    softDelete = SoftDelete.ACTIVE,
                ).applyWithDomainModel(member)
            }
        }
    }
}
