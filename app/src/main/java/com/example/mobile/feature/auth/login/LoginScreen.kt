package com.example.mobile.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.data.auth.AuthRepositoryFake

/**
 * LoginScreen: поля email/пароль, кнопки входа, лоадер и диалог ошибок.
 */
@Composable
fun LoginScreen(
    onBackToRegister: () -> Unit,
    onLoginSuccess: (String) -> Unit,
    vm: LoginViewModel = run {
        val repo = remember { AuthRepositoryFake() }
        viewModel(factory = LoginViewModel.Factory(repo))
    }
) {
    val ui by vm.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Вход", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = ui.email,
            onValueChange = vm::onEmailChanged,
            label = { Text("Электронная почта") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = ui.password,
            onValueChange = vm::onPasswordChanged,
            label = { Text("Пароль") },
            singleLine = true,
            visualTransformation = if (ui.passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
        TextButton(onClick = vm::onTogglePassword) {
            Text(if (ui.passwordVisible) "Скрыть пароль" else "Показать пароль")
        }

        Spacer(Modifier.height(12.dp))
        Button(
            onClick = { vm.login(onLoginSuccess) },
            enabled = ui.isFormValid && !ui.isLoading
        ) { Text("Войти") }

        Spacer(Modifier.height(8.dp))
        Button(onClick = { vm.loginWithGoogle(onLoginSuccess) }, enabled = !ui.isLoading) {
            Text("Войти через Google")
        }

        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onBackToRegister) { Text("Зарегистрироваться") }

        if (ui.isLoading) {
            Spacer(Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }

    if (ui.errorMessage != null) {
        AlertDialog(
            onDismissRequest = vm::dismissError,
            confirmButton = {
                TextButton(onClick = vm::dismissError) { Text("Ок") }
            },
            title = { Text("Ошибка") },
            text = { Text(ui.errorMessage ?: "") }
        )
    }
}


