package com.module.api.security

import com.module.api.security.context.CustomAuthenticationProvider
import com.module.api.security.context.ProviderAuthenticationEntryPoint
import com.module.api.security.filter.AuthorizationToMemberIdHeaderFilter
import com.module.domain.port.outport.TokenPort
import com.module.member.port.outport.LoginSessionPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(
    private val tokenPort: TokenPort,
    private val loginSessionPort: LoginSessionPort,
) {
    private val antPathRequestMatchers =
        WhiteList.WHITE_LIST.map { pattern -> AntPathRequestMatcher(pattern) }.toTypedArray()

    @Order(1)
    @Bean
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .securityMatcher(AntPathRequestMatcher("/api/**"))
            .cors {}
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .addFilterBefore(
                corsFilter(),
                UsernamePasswordAuthenticationFilter::class.java,
            )
            .addFilterBefore(
                authorizationToMemberFilter(),
                UsernamePasswordAuthenticationFilter::class.java,
            )
            .authorizeHttpRequests {
                it.requestMatchers(
                    CorsUtils::isPreFlightRequest,
                ).permitAll()
                it.requestMatchers(*antPathRequestMatchers).permitAll()
                it.anyRequest().authenticated()
            }
            .exceptionHandling { it.authenticationEntryPoint(exceptionHandler()) }
            .build()
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val configuration =
            CorsConfiguration().apply {
                allowedOriginPatterns = listOf("*")
                allowedMethods = HttpMethod.values().map { it.name() }.toList()
                allowedHeaders =
                    listOf(
                        HttpHeaders.CONTENT_TYPE,
                        HttpHeaders.ACCEPT,
                        HttpHeaders.AUTHORIZATION,
                    )
                allowCredentials = true
            }
        val source =
            UrlBasedCorsConfigurationSource().apply {
                registerCorsConfiguration("/**", configuration)
            }

        return CorsFilter(source)
    }

    @Bean
    fun authorizationToMemberFilter(): AuthorizationToMemberIdHeaderFilter {
        return AuthorizationToMemberIdHeaderFilter(
            tokenPort = tokenPort,
            loginSessionPort = loginSessionPort,
            authenticationManager = authenticationManager(),
        )
    }

    fun authenticationManager(): AuthenticationManager {
        return AuthenticationManager { authentication ->
            customAuthenticationProvider().authenticate(authentication)
        }
    }

    fun customAuthenticationProvider(): AuthenticationProvider {
        return CustomAuthenticationProvider()
    }

    @Bean
    fun exceptionHandler(): ProviderAuthenticationEntryPoint {
        return ProviderAuthenticationEntryPoint()
    }
}
