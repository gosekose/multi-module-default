package com.module.api.security.context

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.util.Collections

data class AuthenticationContext(
    val userId: String,
) : Serializable, UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return Collections.singletonList(ROLE_USER)
    }

    override fun getPassword(): String {
        return userId
    }

    override fun getUsername(): String {
        return userId
    }

    companion object {
        private val ROLE_USER = SimpleGrantedAuthority("ROLE_USER")
    }
}
