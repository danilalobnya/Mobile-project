package com.example.mobile.data.local

import com.example.mobile.domain.model.User
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * LocalDataSource: интерфейс для работы с локальными данными.
 * Определяет операции для работы с локальным хранилищем (DataStore, Room и т.д.).
 */
interface LocalDataSource {
    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): String?
    suspend fun clearAccessToken()
    
    suspend fun saveUser(user: User)
    suspend fun getCurrentUser(): User?
    suspend fun clearUser()
    
    suspend fun setOnboardingCompleted(completed: Boolean)
    suspend fun isOnboardingCompleted(): Boolean
}

/**
 * LocalDataSourceImpl: реализация LocalDataSource.
 * Использует DataStore для хранения данных.
 */
class LocalDataSourceImpl @Inject constructor(
    private val appPreferences: com.example.mobile.core.datastore.AppPreferences
) : LocalDataSource {
    
    override suspend fun saveAccessToken(token: String) {
        appPreferences.setAccessToken(token)
    }
    
    override suspend fun getAccessToken(): String? {
        return appPreferences.accessTokenFlow.first()
    }
    
    override suspend fun clearAccessToken() {
        appPreferences.setAccessToken("")
    }
    
    override suspend fun saveUser(user: User) {
        // В реальном приложении здесь нужно сериализовать User в JSON
        // и сохранить в DataStore или Room
        // Для демонстрации просто сохраняем email
        appPreferences.setAccessToken(user.email) // Временная заглушка
    }
    
    override suspend fun getCurrentUser(): User? {
        // В реальном приложении здесь нужно десериализовать User из JSON
        // Для демонстрации возвращаем null
        return null
    }
    
    override suspend fun clearUser() {
        // Очищаем данные пользователя
    }
    
    override suspend fun setOnboardingCompleted(completed: Boolean) {
        appPreferences.setOnboardingCompleted(completed)
    }
    
    override suspend fun isOnboardingCompleted(): Boolean {
        return appPreferences.onboardingCompletedFlow.first()
    }
}
