package com.example.mobile.feature.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * LoginViewModel: хранит состояние экрана входа и выполняет проверки/запросы через репозиторий.
 */
class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    data class UiState(
        val email: String = "",
        val password: String = "",
        val passwordVisible: Boolean = false,
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val isFormValid: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onEmailChanged(value: String) {
        _uiState.value = _uiState.value.copy(email = value)
        validateForm()
    }

    fun onPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
        validateForm()
    }

    fun onTogglePassword() {
        _uiState.value = _uiState.value.copy(passwordVisible = !_uiState.value.passwordVisible)
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(passwordVisible = !_uiState.value.passwordVisible)
    }


    private fun validateForm() {
        val emailOk = EMAIL_REGEX.toRegex().matches(_uiState.value.email)
        val passOk = _uiState.value.password.isNotBlank()
        _uiState.value = _uiState.value.copy(isFormValid = emailOk && passOk)
    }

    /**
     * Имитируем запрос на сервер: успешный, если email содержит "@" и пароль длиной >= 6.
     * Возвращаем фейковый токен через callback.
     */
    fun login(onSuccess: (String) -> Unit) {
        if (!_uiState.value.isFormValid || _uiState.value.isLoading) return
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val token = authRepository.loginWithEmail(_uiState.value.email, _uiState.value.password)
                onSuccess(token)
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (t: Throwable) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = t.message ?: "Ошибка авторизации"
                )
            }
        }
    }

    /**
     * Заглушка авторизации через Google.
     */
    fun loginWithGoogle(onSuccess: (String) -> Unit) {
        if (_uiState.value.isLoading) return
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val token = authRepository.loginWithGoogle()
                onSuccess(token)
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (t: Throwable) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = t.message ?: "Ошибка авторизации"
                )
            }
        }
    }

    companion object {
        private const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    }
    class Factory(private val repo: AuthRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(repo) as T
        }
    }
}


