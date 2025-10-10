package com.example.mobile.data.auth

/**
 * AuthRepository: контракт для авторизации/регистрации (будет реализация через Supabase).
 */
interface AuthRepository {
    /** Возвращает access-токен при успешном входе. Бросает исключение с текстом ошибки при неудаче. */
    suspend fun loginWithEmail(email: String, password: String): String

    /** Возвращает access-токен при успешной OAuth-авторизации (Google). */
    suspend fun loginWithGoogle(): String
}


