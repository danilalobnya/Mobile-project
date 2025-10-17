package com.example.mobile.domain.service

import com.example.mobile.domain.model.Gender
import com.example.mobile.domain.model.RegistrationData
import javax.inject.Inject

/**
 * ValidationService: сервис для валидации данных.
 * Содержит бизнес-правила валидации различных типов данных.
 */
class ValidationService @Inject constructor() {
    
    /**
     * ValidationResult: результат валидации.
     */
    data class ValidationResult(
        val isValid: Boolean,
        val errorMessage: String? = null
    )
    
    /**
     * Валидирует email адрес.
     */
    fun validateEmail(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(false, "Email не может быть пустым")
        }
        
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        if (!emailRegex.matches(email)) {
            return ValidationResult(false, "Некорректный формат email")
        }
        
        return ValidationResult(true)
    }
    
    /**
     * Валидирует пароль.
     */
    fun validatePassword(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(false, "Пароль не может быть пустым")
        }
        
        if (password.length < 6) {
            return ValidationResult(false, "Пароль должен содержать минимум 6 символов")
        }
        
        return ValidationResult(true)
    }
    
    /**
     * Валидирует дату в формате DD.MM.YYYY.
     */
    fun validateDate(date: String): ValidationResult {
        if (date.isBlank()) {
            return ValidationResult(false, "Дата не может быть пустой")
        }
        
        val dateRegex = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$".toRegex()
        if (!dateRegex.matches(date)) {
            return ValidationResult(false, "Некорректный формат даты (DD.MM.YYYY)")
        }
        
        return ValidationResult(true)
    }
    
    /**
     * Валидирует номер водительского удостоверения.
     */
    fun validateDriverLicenseNumber(number: String): ValidationResult {
        if (number.isBlank()) {
            return ValidationResult(false, "Номер водительского удостоверения не может быть пустым")
        }
        
        val numberRegex = "^\\d{10}$".toRegex()
        if (!numberRegex.matches(number)) {
            return ValidationResult(false, "Номер должен содержать ровно 10 цифр")
        }
        
        return ValidationResult(true)
    }
    
    /**
     * Валидирует все данные регистрации.
     */
    fun validateRegistrationData(data: RegistrationData): ValidationResult {
        // Валидация email
        val emailValidation = validateEmail(data.email)
        if (!emailValidation.isValid) return emailValidation
        
        // Валидация паролей
        val passwordValidation = validatePassword(data.password)
        if (!passwordValidation.isValid) return passwordValidation
        
        if (data.password != data.confirmPassword) {
            return ValidationResult(false, "Пароли не совпадают")
        }
        
        // Валидация имени
        if (data.firstName.isBlank()) {
            return ValidationResult(false, "Имя не может быть пустым")
        }
        
        if (data.lastName.isBlank()) {
            return ValidationResult(false, "Фамилия не может быть пустой")
        }
        
        // Валидация даты рождения
        val dateValidation = validateDate(data.dateOfBirth)
        if (!dateValidation.isValid) return dateValidation
        
        // Валидация водительского удостоверения
        data.driverLicense?.let { license ->
            val licenseValidation = validateDriverLicenseNumber(license.number)
            if (!licenseValidation.isValid) return licenseValidation
            
            val issueDateValidation = validateDate(license.issueDate)
            if (!issueDateValidation.isValid) return issueDateValidation
        }
        
        return ValidationResult(true)
    }
}
