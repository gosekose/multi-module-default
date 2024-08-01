package com.module.api.security.manager

import com.module.api.security.filter.XMemberIdHeader.X_MEMBER_ID
import com.module.domain.exception.BusinessErrorCause
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.util.AntPathMatcher

fun HttpServletRequest.getPathVariable(
    name: String,
    pattern: String,
): String {
    val matcher = AntPathMatcher()
    val variables = matcher.extractUriTemplateVariables(pattern, this.requestURI)
    return variables[name] ?: throw InsufficientAuthenticationException(BusinessErrorCause.UNAUTHORIZED.message)
}

fun HttpServletRequest.getMemberId(): Long {
    return this.getHeader(X_MEMBER_ID).toLong()
}
