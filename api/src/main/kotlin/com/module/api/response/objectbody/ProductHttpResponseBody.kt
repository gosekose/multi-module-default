package com.module.api.response.objectbody

import com.module.extension.toKSTLocalDateTime
import com.module.product.model.Category
import com.module.product.usecase.inport.dto.WrappedProduct
import java.time.LocalDateTime

data class ProductHttpResponseBody(
    val id: Long,
    val category: CategoryHttpField,
    val price: Long,
    val cost: Double,
    val name: String,
    val description: String,
    val barCode: String,
    val expiryDateAt: LocalDateTime,
    val size: String,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime,
    val fileId: Long?,
    val url: String?,
) {
    companion object {
        @JvmStatic
        fun from(wrappedProduct: WrappedProduct): ProductHttpResponseBody {
            return with(wrappedProduct.product) {
                ProductHttpResponseBody(
                    id = id,
                    category = CategoryHttpField.from(category),
                    price = price,
                    cost = cost,
                    name = name,
                    description = description,
                    barCode = barCode,
                    expiryDateAt = expiryDateAt.toKSTLocalDateTime(),
                    size = size.name.lowercase(),
                    createdAt = createdAt.toKSTLocalDateTime(),
                    lastModifiedAt = lastModifiedAt.toKSTLocalDateTime(),
                    fileId = wrappedProduct.fileMetadata?.id,
                    url = wrappedProduct.fileMetadata?.vendor?.actualUrl,
                )
            }
        }
    }

    data class CategoryHttpField(
        val category: Category,
        val koreanName: String,
        val englishName: String,
    ) {
        companion object {
            @JvmStatic
            fun from(category: Category): CategoryHttpField {
                return with(category) {
                    CategoryHttpField(
                        category = category,
                        koreanName = koreanName,
                        englishName = englishName,
                    )
                }
            }
        }
    }
}
