package com.example.mobile.feature.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Step1Screen: ввод email, пароля и подтверждения, чекбокс согласия.
 * Отображает ошибки согласно требованиям.
 */
@Composable
fun Step1Screen(onNext: () -> Unit, onBack: () -> Unit) {
    val email = remember { mutableStateOf("") }
    val pass = remember { mutableStateOf("") }
    val pass2 = remember { mutableStateOf("") }
    val showPass = remember { mutableStateOf(false) }
    val showPass2 = remember { mutableStateOf(false) }
    val agreed = remember { mutableStateOf(false) }
    val error: MutableState<String?> = remember { mutableStateOf(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Регистрация – Шаг 1", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Электронная почта") })
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = pass.value,
            onValueChange = { pass.value = it },
            label = { Text("Придумайте пароль") },
            visualTransformation = if (showPass.value) VisualTransformation.None else PasswordVisualTransformation()
        )
        TextButton(onClick = { showPass.value = !showPass.value }) {
            Text(if (showPass.value) "Скрыть пароль" else "Показать пароль")
        }
        OutlinedTextField(
            value = pass2.value,
            onValueChange = { pass2.value = it },
            label = { Text("Повторите пароль") },
            visualTransformation = if (showPass2.value) VisualTransformation.None else PasswordVisualTransformation()
        )
        TextButton(onClick = { showPass2.value = !showPass2.value }) {
            Text(if (showPass2.value) "Скрыть пароль" else "Показать пароль")
        }

        Spacer(Modifier.height(6.dp))
        RowAgree(agreed)

        Spacer(Modifier.height(12.dp))
        Button(onClick = onBack) { Text("Назад") }
        Spacer(Modifier.height(4.dp))
        Button(onClick = {
            when {
                !EMAIL_REGEX.toRegex().matches(email.value) -> error.value = "Введите корректный адрес электронной почты."
                pass.value != pass2.value -> error.value = "Пароли не совпадают."
                !agreed.value -> error.value = "Необходимо согласиться с условиями обслуживания и политикой конфиденциальности."
                else -> onNext()
            }
        }) { Text("Далее") }
    }

    if (error.value != null) {
        AlertDialog(
            onDismissRequest = { error.value = null },
            confirmButton = { TextButton(onClick = { error.value = null }) { Text("Ок") } },
            title = { Text("Ошибка") },
            text = { Text(error.value ?: "") }
        )
    }
}

@Composable
private fun RowAgree(agreed: MutableState<Boolean>) {
    androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = agreed.value, onCheckedChange = { agreed.value = it })
        Text("Согласен с условиями и политикой")
    }
}

private const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"


