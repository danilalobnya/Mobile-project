package com.example.mobile.domain.usecase

import com.example.mobile.domain.model.AuthResult
import com.example.mobile.domain.model.RegistrationData
import com.example.mobile.domain.repository.AuthRepository
import com.example.mobile.domain.service.ValidationService
import javax.inject.Inject

/**
 * RegisterUseCase: сценарий взаимодействия для регистрации нового пользователя.
 * Содержит бизнес-логику валидации и обработки регистрации.
 */
class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val validationService: ValidationService
) {
    
    /**
     * Выполняет регистрацию нового пользователя с валидацией данных.
     * @param registrationData Данные для регистрации
     * @return AuthResult с результатом операции
     */
    suspend operator fun invoke(registrationData: RegistrationData): AuthResult {
        // Валидация всех полей регистрации
        val validationResult = validationService.validateRegistrationData(registrationData)
        if (!validationResult.isValid) {
            return AuthResult.Error(
                message = validationResult.errorMessage ?: "Ошибка валидации данных",
                type = com.example.mobile.domain.model.AuthErrorType.VALIDATION_ERROR
            )
        }
        
        // Выполнение регистрации через репозиторий
        return authRepository.register(registrationData)
    }
}
