package com.module.api.response.objectbody

data class CursorProductHttpResponseBody(
    val products: List<ProductHttpResponseBody>,
    val total: Int,
    val lastCursorId: Long?,
    val hasNext: Boolean,
) {
    companion object {
        fun of(
            products: List<ProductHttpResponseBody>,
            total: Int,
            lastCursorId: Long?,
            hasNext: Boolean,
        ): CursorProductHttpResponseBody {
            return CursorProductHttpResponseBody(
                products = products,
                total = total,
                lastCursorId = lastCursorId,
                hasNext = hasNext,
            )
        }
    }
}