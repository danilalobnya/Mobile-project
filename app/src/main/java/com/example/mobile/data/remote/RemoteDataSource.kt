package com.example.mobile.data.remote

import com.example.mobile.domain.model.RegistrationData
import com.example.mobile.data.repository.UserDataResponse
import javax.inject.Inject

/**
 * RemoteDataSource: интерфейс для работы с удаленными данными.
 * Определяет операции для работы с API сервера.
 */
interface RemoteDataSource {
    suspend fun login(email: String, password: String): Result<UserDataResponse>
    suspend fun register(registrationData: RegistrationData): Result<UserDataResponse>
    suspend fun loginWithGoogle(googleToken: String): Result<UserDataResponse>
}

/**
 * RemoteDataSourceImpl: реализация RemoteDataSource.
 * Использует HTTP клиент для работы с API.
 */
class RemoteDataSourceImpl @Inject constructor(
    // В реальном приложении здесь будет HTTP клиент (Retrofit, Ktor и т.д.)
    // private val apiService: ApiService
) : RemoteDataSource {
    
    override suspend fun login(email: String, password: String): Result<UserDataResponse> {
        return try {
            // Имитация сетевого запроса
            kotlinx.coroutines.delay(1500)
            
            if (email == "test@example.com" && password == "password") {
                val user = com.example.mobile.domain.model.User(
                    id = "1",
                    email = email,
                    firstName = "Тест",
                    lastName = "Пользователь",
                    dateOfBirth = "01.01.1990",
                    gender = com.example.mobile.domain.model.Gender.MALE
                )
                Result.success(UserDataResponse(user, "fake_access_token_123"))
            } else {
                Result.failure(Exception("Неверный email или пароль"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun register(registrationData: RegistrationData): Result<UserDataResponse> {
        return try {
            // Имитация сетевого запроса
            kotlinx.coroutines.delay(1500)
            
            val user = com.example.mobile.domain.model.User(
                id = "2",
                email = registrationData.email,
                firstName = registrationData.firstName,
                lastName = registrationData.lastName,
                middleName = registrationData.middleName,
                dateOfBirth = registrationData.dateOfBirth,
                gender = registrationData.gender
            )
            Result.success(UserDataResponse(user, "fake_register_token_456"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun loginWithGoogle(googleToken: String): Result<UserDataResponse> {
        return try {
            // Имитация сетевого запроса
            kotlinx.coroutines.delay(1500)
            
            val user = com.example.mobile.domain.model.User(
                id = "3",
                email = "google@example.com",
                firstName = "Google",
                lastName = "User",
                dateOfBirth = "01.01.1990",
                gender = com.example.mobile.domain.model.Gender.MALE
            )
            Result.success(UserDataResponse(user, "google_oauth_token_789"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
