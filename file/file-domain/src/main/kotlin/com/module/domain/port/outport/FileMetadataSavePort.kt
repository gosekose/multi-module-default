package com.module.domain.port.outport

import com.module.domain.model.FileMetadata

interface FileMetadataSavePort {
    fun save(fileMetadata: FileMetadata): FileMetadata
}