package com.example.mobile.domain.repository

import com.example.mobile.domain.model.AuthResult
import com.example.mobile.domain.model.RegistrationData
import com.example.mobile.domain.model.User

/**
 * AuthRepository: интерфейс репозитория для работы с аутентификацией.
 * Определяет контракт для операций входа и регистрации пользователей.
 * 
 * Этот интерфейс находится в domain слое и не зависит от конкретных
 * реализаций (фреймворков, баз данных, сетевых библиотек).
 */
interface AuthRepository {
    
    /**
     * Выполняет вход пользователя в систему.
     * @param email Электронная почта пользователя
     * @param password Пароль пользователя
     * @return AuthResult с результатом операции (успех или ошибка)
     */
    suspend fun login(email: String, password: String): AuthResult
    
    /**
     * Регистрирует нового пользователя в системе.
     * @param registrationData Данные для регистрации
     * @return AuthResult с результатом операции (успех или ошибка)
     */
    suspend fun register(registrationData: RegistrationData): AuthResult
    
    /**
     * Выполняет вход через Google OAuth.
     * @param googleToken Токен от Google
     * @return AuthResult с результатом операции
     */
    suspend fun loginWithGoogle(googleToken: String): AuthResult
    
    /**
     * Получает текущего авторизованного пользователя.
     * @return User если пользователь авторизован, null иначе
     */
    suspend fun getCurrentUser(): User?
    
    /**
     * Выходит из системы (очищает токены и сессию).
     */
    suspend fun logout()
    
    /**
     * Проверяет, авторизован ли пользователь.
     * @return true если пользователь авторизован
     */
    suspend fun isAuthenticated(): Boolean
}
