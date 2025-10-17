package com.example.mobile.domain.model

/**
 * AuthResult: результат операции аутентификации.
 * Может содержать либо успешный результат с пользователем и токеном,
 * либо ошибку аутентификации.
 */
sealed class AuthResult {
    data class Success(
        val user: User,
        val accessToken: String
    ) : AuthResult()
    
    data class Error(
        val message: String,
        val type: AuthErrorType
    ) : AuthResult()
}

/**
 * AuthErrorType: типы ошибок аутентификации.
 */
enum class AuthErrorType {
    INVALID_CREDENTIALS,
    NETWORK_ERROR,
    SERVER_ERROR,
    VALIDATION_ERROR,
    UNKNOWN_ERROR
}
