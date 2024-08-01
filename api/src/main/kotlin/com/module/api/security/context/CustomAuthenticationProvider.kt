package com.module.api.security.context

import com.module.domain.exception.ApplicationBusinessException
import com.module.domain.exception.BusinessErrorCause
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication

class CustomAuthenticationProvider : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        try {
            val userDetails = (authentication.principal as AuthenticationContext)
            return UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails,
                userDetails.authorities,
            )
        } catch (e: Exception) {
            throw ApplicationBusinessException(BusinessErrorCause.UNAUTHORIZED)
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
