package com.example.mobile.data.repository

import com.example.mobile.domain.model.AuthResult
import com.example.mobile.domain.model.AuthErrorType
import com.example.mobile.domain.model.Gender
import com.example.mobile.domain.model.RegistrationData
import com.example.mobile.domain.model.User
import com.example.mobile.domain.repository.AuthRepository
import com.example.mobile.data.local.LocalDataSource
import com.example.mobile.data.remote.RemoteDataSource
import javax.inject.Inject

/**
 * AuthRepositoryImpl: реализация AuthRepository в data слое.
 * Координирует работу между локальными и удаленными источниками данных.
 */
class AuthRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : AuthRepository {
    
    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            // Выполняем запрос к серверу
            val response = remoteDataSource.login(email, password)
            
            if (response.isSuccess) {
                val userData = response.getOrNull()!!
                val token = userData.accessToken
                
                // Сохраняем данные локально
                localDataSource.saveAccessToken(token)
                localDataSource.saveUser(userData.user)
                
                AuthResult.Success(userData.user, token)
            } else {
                val error = response.exceptionOrNull()
                AuthResult.Error(
                    message = error?.message ?: "Ошибка входа",
                    type = AuthErrorType.SERVER_ERROR
                )
            }
        } catch (e: Exception) {
            AuthResult.Error(
                message = e.message ?: "Неизвестная ошибка",
                type = AuthErrorType.NETWORK_ERROR
            )
        }
    }
    
    override suspend fun register(registrationData: RegistrationData): AuthResult {
        return try {
            // Выполняем запрос к серверу
            val response = remoteDataSource.register(registrationData)
            
            if (response.isSuccess) {
                val userData = response.getOrNull()!!
                val token = userData.accessToken
                
                // Сохраняем данные локально
                localDataSource.saveAccessToken(token)
                localDataSource.saveUser(userData.user)
                
                AuthResult.Success(userData.user, token)
            } else {
                val error = response.exceptionOrNull()
                AuthResult.Error(
                    message = error?.message ?: "Ошибка регистрации",
                    type = AuthErrorType.SERVER_ERROR
                )
            }
        } catch (e: Exception) {
            AuthResult.Error(
                message = e.message ?: "Неизвестная ошибка",
                type = AuthErrorType.NETWORK_ERROR
            )
        }
    }
    
    override suspend fun loginWithGoogle(googleToken: String): AuthResult {
        return try {
            val response = remoteDataSource.loginWithGoogle(googleToken)
            
            if (response.isSuccess) {
                val userData = response.getOrNull()!!
                val token = userData.accessToken
                
                localDataSource.saveAccessToken(token)
                localDataSource.saveUser(userData.user)
                
                AuthResult.Success(userData.user, token)
            } else {
                val error = response.exceptionOrNull()
                AuthResult.Error(
                    message = error?.message ?: "Ошибка входа через Google",
                    type = AuthErrorType.SERVER_ERROR
                )
            }
        } catch (e: Exception) {
            AuthResult.Error(
                message = e.message ?: "Неизвестная ошибка",
                type = AuthErrorType.NETWORK_ERROR
            )
        }
    }
    
    override suspend fun getCurrentUser(): User? {
        return localDataSource.getCurrentUser()
    }
    
    override suspend fun logout() {
        localDataSource.clearAccessToken()
        localDataSource.clearUser()
    }
    
    override suspend fun isAuthenticated(): Boolean {
        val token = localDataSource.getAccessToken()
        return !token.isNullOrBlank()
    }
}

/**
 * UserDataResponse: ответ сервера с данными пользователя.
 */
data class UserDataResponse(
    val user: User,
    val accessToken: String
)
