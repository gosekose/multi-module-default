package com.module.jwt.adapter

import com.module.domain.exception.ApplicationBusinessException
import com.module.domain.exception.BusinessErrorCause
import com.module.domain.model.Token
import com.module.domain.port.outport.TokenPort
import com.module.jwt.adapter.configuration.TokenProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.Date
import javax.crypto.SecretKey

@Component
@EnableConfigurationProperties(TokenProperties::class)
class TokenAdapter(
    private val tokenProperties: TokenProperties,
) : TokenPort {
    private var secretKey: SecretKey = Keys.hmacShaKeyFor(tokenProperties.secretKey.toByteArray())

    override fun create(
        subject: String,
        subjectId: Long,
    ): Token {
        return Token(
            accessToken =
                Jwts.builder()
                    .subject(subject)
                    .signWith(secretKey)
                    .expiration(Date.from(Instant.now().plusMillis(tokenProperties.expirationMillis)))
                    .compact(),
            subjectId = subjectId,
        )
    }

    override fun parse(token: String): String {
        return runCatching {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload
                .subject
        }.getOrElse {
            throw ApplicationBusinessException(BusinessErrorCause.UNAUTHORIZED)
        }
    }
}
