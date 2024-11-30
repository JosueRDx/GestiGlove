package com.josuerdx.gestiglove.utils.validation

import android.util.Patterns

/**
 * Para validar contraseñas y correos.
 */
object InputValidator {

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

    fun isEmailFormatValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isEmailReal(email: String): Boolean {
        val knownDomains = listOf(
            "gmail.com", "yahoo.com", "hotmail.com", "outlook.com",
        )

        val parts = email.split("@")
        if (parts.size != 2) return false

        val domain = parts[1].lowercase()
        return domain in knownDomains || domain.endsWith(".edu")
    }
}