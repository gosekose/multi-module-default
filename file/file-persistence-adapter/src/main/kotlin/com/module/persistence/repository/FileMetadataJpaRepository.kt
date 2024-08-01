package com.module.persistence.repository

import com.module.persistence.base.SoftDelete
import com.module.persistence.entity.FileMetadataEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FileMetadataJpaRepository : JpaRepository<FileMetadataEntity, Long> {
    fun findByIdAndSoftDelete(
        id: Long,
        status: SoftDelete = SoftDelete.ACTIVE,
    ): FileMetadataEntity?

    fun findByIdInAndSoftDelete(
        ids: List<Long>,
        status: SoftDelete = SoftDelete.ACTIVE,
    ): List<FileMetadataEntity>

    fun findByIdInAndMemberIdAndSoftDelete(
        ids: List<Long>,
        memberId: Long,
        status: SoftDelete = SoftDelete.ACTIVE,
    ): List<FileMetadataEntity>
}