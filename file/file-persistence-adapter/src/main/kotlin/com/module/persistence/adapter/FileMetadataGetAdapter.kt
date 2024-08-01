package com.module.persistence.adapter

import com.module.domain.exception.BusinessErrorCause
import com.module.domain.model.FileMetadata
import com.module.domain.port.outport.FileMetadataGetPort
import com.module.domain.validation.returnIfNotNull
import com.module.persistence.repository.FileMetadataJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class FileMetadataGetAdapter(
    private val fileMetadataJpaRepository: FileMetadataJpaRepository,
) : FileMetadataGetPort {
    override fun getById(id: Long): FileMetadata {
        return returnIfNotNull(
            fileMetadataJpaRepository.findByIdAndSoftDelete(id),
            BusinessErrorCause.FILE_NOT_FOUND
        ).toModel()
    }

    override fun getByIds(ids: List<Long>): Set<FileMetadata> {
        return fileMetadataJpaRepository.findByIdInAndSoftDelete(ids).map { it.toModel() }
            .toSet()
    }

    override fun getByIdsAndMemberId(
        ids: List<Long>,
        memberId: Long,
    ): List<FileMetadata> {
        return fileMetadataJpaRepository.findByIdInAndMemberIdAndSoftDelete(
            ids = ids,
            memberId = memberId,
        ).map { it.toModel() }
    }
}