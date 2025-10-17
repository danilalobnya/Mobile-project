package com.example.mobile.domain.error

/**
 * AppError: базовая система обработки ошибок приложения.
 * Определяет все возможные типы ошибок в системе.
 */
sealed class AppError : Exception() {
    
    /**
     * Ошибки сети.
     */
    sealed class NetworkError : AppError() {
        object NoInternetConnection : NetworkError()
        object Timeout : NetworkError()
        object ServerUnavailable : NetworkError()
        data class HttpError(val code: Int, val errorMessage: String) : NetworkError()
    }
    
    /**
     * Ошибки валидации.
     */
    sealed class ValidationError : AppError() {
        data class InvalidEmail(val email: String) : ValidationError()
        data class InvalidPassword(val reason: String) : ValidationError()
        data class InvalidDate(val date: String) : ValidationError()
        data class InvalidDriverLicense(val number: String) : ValidationError()
        object PasswordsDoNotMatch : ValidationError()
        data class RequiredFieldMissing(val fieldName: String) : ValidationError()
    }
    
    /**
     * Ошибки аутентификации.
     */
    sealed class AuthError : AppError() {
        object InvalidCredentials : AuthError()
        object UserNotFound : AuthError()
        object UserAlreadyExists : AuthError()
        object TokenExpired : AuthError()
        object AccountBlocked : AuthError()
    }
    
    /**
     * Ошибки данных.
     */
    sealed class DataError : AppError() {
        object DataNotFound : DataError()
        object DataCorrupted : DataError()
        object DatabaseError : DataError()
    }
    
    /**
     * Неизвестные ошибки.
     */
    data class UnknownError(val errorMessage: String) : AppError()
}

/**
 * Функция для преобразования AppError в пользовательское сообщение.
 */
fun AppError.toUserMessage(): String {
    return when (this) {
        is AppError.NetworkError.NoInternetConnection -> "Нет подключения к интернету"
        is AppError.NetworkError.Timeout -> "Превышено время ожидания"
        is AppError.NetworkError.ServerUnavailable -> "Сервер недоступен"
        is AppError.NetworkError.HttpError -> "Ошибка сервера: ${this.errorMessage}"
        
        is AppError.ValidationError.InvalidEmail -> "Некорректный email"
        is AppError.ValidationError.InvalidPassword -> "Некорректный пароль: ${this.reason}"
        is AppError.ValidationError.InvalidDate -> "Некорректная дата"
        is AppError.ValidationError.InvalidDriverLicense -> "Некорректный номер водительского удостоверения"
        is AppError.ValidationError.PasswordsDoNotMatch -> "Пароли не совпадают"
        is AppError.ValidationError.RequiredFieldMissing -> "Поле '${this.fieldName}' обязательно для заполнения"
        
        is AppError.AuthError.InvalidCredentials -> "Неверный email или пароль"
        is AppError.AuthError.UserNotFound -> "Пользователь не найден"
        is AppError.AuthError.UserAlreadyExists -> "Пользователь с таким email уже существует"
        is AppError.AuthError.TokenExpired -> "Сессия истекла, войдите заново"
        is AppError.AuthError.AccountBlocked -> "Аккаунт заблокирован"
        
        is AppError.DataError.DataNotFound -> "Данные не найдены"
        is AppError.DataError.DataCorrupted -> "Ошибка данных"
        is AppError.DataError.DatabaseError -> "Ошибка базы данных"
        
        is AppError.UnknownError -> this.errorMessage
    }
}
