package com.module.domain.port.outport

import com.module.domain.model.FileMetadata

interface FileMetadataGetPort {
    fun getById(id: Long): FileMetadata

    fun getByIds(ids: List<Long>): Set<FileMetadata>

    fun getByIdsAndMemberId(
        ids: List<Long>,
        memberId: Long,
    ): List<FileMetadata>
}