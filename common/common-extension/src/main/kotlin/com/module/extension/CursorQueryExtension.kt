package com.module.extension

data class FilteredCursorQuery<T>(
    val memberId: Long,
    val lastCursorId: Long?,
    val size: Long,
    val order: CursorOrder,
    val filter: T?,
)

data class CursorResponse<T>(
    val data: List<T>,
    val total: Int,
    val lastCursorId: Long?,
    val hasNext: Boolean,
)

enum class CursorOrder {
    ASC,
    DESC,
}
