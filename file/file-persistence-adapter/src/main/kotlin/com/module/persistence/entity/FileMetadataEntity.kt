package com.module.persistence.entity

import com.module.domain.model.FileMetadata
import com.module.persistence.base.BaseJpaEntity
import com.module.persistence.base.SoftDelete
import com.module.persistence.base.applyWithDomainModel
import com.module.persistence.base.applyWithEntity
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
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
    name = "file_metadatas",
    indexes = [
        Index(name = "idx_file_matadatas__member_id", columnList = "member_id"),
    ],
)
class FileMetadataEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "member_id", columnDefinition = "BIGINT", nullable = false)
    val memberId: Long,
    @Column(name = "original_file_name", columnDefinition = "VARCHAR(255)", nullable = false)
    val originalFileName: String,
    @Embedded
    val vendorMetadata: VendorMetadataField?,
    @Column(name = "fileKey", columnDefinition = "VARCHAR(255)", nullable = false)
    val fileKey: String,
    @Enumerated(value = EnumType.STRING)
    @Column(name = "soft_delete_status", columnDefinition = "VARCHAR(16)", nullable = false)
    var softDelete: SoftDelete,
) : BaseJpaEntity() {

    @Embeddable
    data class VendorMetadataField(
        @Enumerated(value = EnumType.STRING)
        @Column(name = "vendor", columnDefinition = "VARCHAR(32)", nullable = true)
        val vendor: FileMetadata.Vendor,
        @Column(name = "actual_url", columnDefinition = "TEXT", nullable = true)
        val actualUrl: String,
    )


    fun toModel(): FileMetadata {
        return FileMetadata(
            id = id,
            memberId = memberId,
            originalFileName = originalFileName,
            vendor = vendorMetadata?.let { FileMetadata.VendorMetadata(it.vendor, it.actualUrl) },
            fileKey = fileKey
        ).applyWithEntity(this)
    }

    companion object {
        @JvmStatic
        fun from(fileMetadata: FileMetadata): FileMetadataEntity {
            return with(fileMetadata) {
                FileMetadataEntity(
                    id = id,
                    memberId = memberId,
                    originalFileName = originalFileName,
                    vendorMetadata = vendor?.let { VendorMetadataField(it.vendor, it.actualUrl) },
                    fileKey = fileKey,
                    softDelete = SoftDelete.ACTIVE,
                )
            }.applyWithDomainModel(fileMetadata)
        }
    }
}