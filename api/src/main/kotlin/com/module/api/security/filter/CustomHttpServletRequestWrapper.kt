package com.module.api.security.filter

import com.module.api.security.filter.XMemberIdHeader.X_MEMBER_ID
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import java.util.Collections
import java.util.Enumeration

class CustomHttpServletRequestWrapper(
    request: HttpServletRequest,
    private val memberId: String
) : HttpServletRequestWrapper(request) {

    override fun getHeader(name: String): String? {
        return if (name.equals(X_MEMBER_ID, ignoreCase = true)) {
            memberId
        } else {
            super.getHeader(name)
        }
    }

    override fun getHeaders(name: String): Enumeration<String> {
        return if (name.equals(X_MEMBER_ID, ignoreCase = true)) {
            Collections.enumeration(listOf(memberId))
        } else {
            super.getHeaders(name)
        }
    }

    override fun getHeaderNames(): Enumeration<String> {
        val names = Collections.list(super.getHeaderNames()) + X_MEMBER_ID
        return Collections.enumeration(names)
    }
}