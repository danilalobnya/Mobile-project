package com.example.mobile.data.auth

import kotlinx.coroutines.delay

/**
 * AuthRepositoryFake: временная реализация без сети.
 * Заменим на Supabase реализацию позже.
 */
class AuthRepositoryFake : AuthRepository {
    override suspend fun loginWithEmail(email: String, password: String): String {
        delay(800)
        if (password.length >= 6) return "fake_token_123"
        else throw IllegalArgumentException("Ошибка авторизации. Проверьте данные.")
    }

    override suspend fun loginWithGoogle(): String {
        delay(600)
        return "google_token_456"
    }
}


