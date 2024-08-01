package com.module.api.security.filter

import com.module.api.security.FilterErrorResponseWrapper
import com.module.api.security.WhiteList.WHITE_LIST
import com.module.api.security.context.AuthenticationContext
import com.module.domain.exception.BusinessErrorCause
import com.module.domain.port.outport.TokenPort
import com.module.domain.validation.returnIfNotNull
import com.module.member.port.outport.LoginSessionPort
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter

class AuthorizationToMemberIdHeaderFilter(
    private val tokenPort: TokenPort,
    private val loginSessionPort: LoginSessionPort,
    private val authenticationManager: AuthenticationManager,
) : OncePerRequestFilter() {
    private val antPathMatcher = AntPathMatcher()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val requestUri = request.requestURI
        if (isWhiteListed(requestUri)) {
            filterChain.doFilter(request, response)
            return
        }

        val accessToken = try {
            val authorizationHeader = request.getHeader(AUTHORIZATION_HEADER)
            parseBearer(authorizationHeader)
        } catch (e: Exception) {
            FilterErrorResponseWrapper.wrap(response)
            return
        }

        val userId = tokenPort.parse(token = accessToken)

        val memberId =
            returnIfNotNull(
                loginSessionPort.getOrNull(userId),
                BusinessErrorCause.UNAUTHORIZED,
            ).subjectId.toString()

        val wrappedRequest = CustomHttpServletRequestWrapper(request, memberId)

        val authenticationToken = setAuthenticationToken(userId)

        authenticationManager.authenticate(authenticationToken).also {
            SecurityContextHolder.getContext().authentication = it
        }

        filterChain.doFilter(wrappedRequest, response)
    }

    private fun setAuthenticationToken(userId: String): UsernamePasswordAuthenticationToken {
        val authenticationContext = AuthenticationContext(userId = userId)
        return UsernamePasswordAuthenticationToken(
            authenticationContext,
            authenticationContext.userId,
            authenticationContext.authorities,
        )
    }

    private fun parseBearer(authorization: String): String {
        return authorization.removePrefix(BEARER).trim()
    }

    private fun isWhiteListed(requestUri: String): Boolean {
        return WHITE_LIST.any { antPathMatcher.match(it, requestUri) }
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER = "Bearer "
    }
}
