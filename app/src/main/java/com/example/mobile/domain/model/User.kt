package com.example.mobile.domain.model

/**
 * User: основная модель пользователя в системе.
 * Представляет бизнес-объект пользователя с его основными данными.
 */
data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val middleName: String? = null,
    val dateOfBirth: String,
    val gender: Gender,
    val profilePhotoUrl: String? = null,
    val driverLicense: DriverLicense? = null,
    val isVerified: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Gender: перечисление полов пользователя.
 */
enum class Gender {
    MALE,
    FEMALE
}

/**
 * DriverLicense: модель водительского удостоверения.
 */
data class DriverLicense(
    val number: String,
    val issueDate: String,
    val licensePhotoUrl: String? = null,
    val passportPhotoUrl: String? = null
)
