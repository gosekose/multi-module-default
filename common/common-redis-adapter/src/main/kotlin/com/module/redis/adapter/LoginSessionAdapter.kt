package com.module.redis.adapter

import com.module.domain.model.Token
import com.module.member.port.outport.LoginSessionPort
import com.module.redis.RedisPort
import org.springframework.stereotype.Component

@Component
class LoginSessionAdapter(
    private val redisPort: RedisPort,
) : LoginSessionPort {
    override fun getOrNull(userId: String): Token? {
        return redisPort.getOrNull(KEY_PREFIX + userId, Token::class.java)
    }

    override fun save(
        userId: String,
        token: Token,
    ) {
        redisPort.set(KEY_PREFIX + userId, token)
    }

    override fun delete(userId: String) {
        redisPort.delete(KEY_PREFIX + userId)
    }

    companion object {
        private const val KEY_PREFIX = "login-session:"
    }
}
