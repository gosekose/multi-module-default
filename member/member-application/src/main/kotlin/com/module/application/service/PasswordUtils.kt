package com.module.application.service

import com.module.domain.exception.ApplicationBusinessException
import com.module.domain.exception.BusinessErrorCause
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Base64

object PasswordUtils {
    @JvmStatic
    fun generateSalt(): String {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return Base64.getEncoder().encodeToString(salt)
    }

    @JvmStatic
    fun hashPassword(
        password: String,
        salt: String,
    ): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val saltedPassword = password + salt
            val hash = digest.digest(saltedPassword.toByteArray())
            Base64.getEncoder().encodeToString(hash)
        } catch (e: NoSuchAlgorithmException) {
            throw ApplicationBusinessException(BusinessErrorCause.INTERNAL_SERVER_ERROR)
        }
    }
}
