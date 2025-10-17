package com.example.mobile.domain.usecase

import com.example.mobile.domain.model.AuthResult
import com.example.mobile.domain.repository.AuthRepository
import com.example.mobile.domain.service.ValidationService
import javax.inject.Inject

/**
 * LoginUseCase: сценарий взаимодействия для входа пользователя в систему.
 * Содержит бизнес-логику валидации и обработки входа.
 */
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val validationService: ValidationService
) {
    
    /**
     * Выполняет вход пользователя с валидацией данных.
     * @param email Электронная почта
     * @param password Пароль
     * @return AuthResult с результатом операции
     */
    suspend operator fun invoke(email: String, password: String): AuthResult {
        // Валидация входных данных
        val emailValidation = validationService.validateEmail(email)
        if (!emailValidation.isValid) {
            return AuthResult.Error(
                message = emailValidation.errorMessage ?: "Некорректный email",
                type = com.example.mobile.domain.model.AuthErrorType.VALIDATION_ERROR
            )
        }
        
        val passwordValidation = validationService.validatePassword(password)
        if (!passwordValidation.isValid) {
            return AuthResult.Error(
                message = passwordValidation.errorMessage ?: "Некорректный пароль",
                type = com.example.mobile.domain.model.AuthErrorType.VALIDATION_ERROR
            )
        }
        
        // Выполнение входа через репозиторий
        return authRepository.login(email, password)
    }
}
