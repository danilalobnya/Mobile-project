package com.example.mobile.domain.model

/**
 * RegistrationData: данные для регистрации пользователя.
 * Содержит все необходимые поля для создания нового пользователя.
 */
data class RegistrationData(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val firstName: String,
    val lastName: String,
    val middleName: String? = null,
    val dateOfBirth: String,
    val gender: Gender,
    val profilePhotoUri: String? = null,
    val driverLicense: DriverLicenseData? = null
)

/**
 * DriverLicenseData: данные водительского удостоверения для регистрации.
 */
data class DriverLicenseData(
    val number: String,
    val issueDate: String,
    val licensePhotoUri: String? = null,
    val passportPhotoUri: String? = null
)
