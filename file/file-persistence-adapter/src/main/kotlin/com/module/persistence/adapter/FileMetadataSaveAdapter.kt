package com.module.persistence.adapter

import com.module.domain.model.FileMetadata
import com.module.domain.port.outport.FileMetadataSavePort
import com.module.persistence.entity.FileMetadataEntity
import com.module.persistence.repository.FileMetadataJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class FileMetadataSaveAdapter(
    private val fileMetadataJpaRepository: FileMetadataJpaRepository,
) : FileMetadataSavePort {
    override fun save(fileMetadata: FileMetadata): FileMetadata {
        return fileMetadataJpaRepository.save(FileMetadataEntity.from(fileMetadata)).toModel()
    }
}