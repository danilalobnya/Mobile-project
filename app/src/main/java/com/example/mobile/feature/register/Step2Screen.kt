package com.example.mobile.feature.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp

/**
 * Step2Screen: ФИО, дата рождения (MM/DD/YYYY), пол (просто текстом для примера).
 */
@Composable
fun Step2Screen(onNext: () -> Unit, onBack: () -> Unit) {
    val lastName = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val middleName = remember { mutableStateOf("") }
    val birthday = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val error: MutableState<String?> = remember { mutableStateOf(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Регистрация – Шаг 2", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = lastName.value, onValueChange = { lastName.value = it }, label = { Text("Фамилия") })
        OutlinedTextField(value = firstName.value, onValueChange = { firstName.value = it }, label = { Text("Имя") })
        OutlinedTextField(value = middleName.value, onValueChange = { middleName.value = it }, label = { Text("Отчество (необязательно)") })
        OutlinedTextField(value = birthday.value, onValueChange = { birthday.value = it }, label = { Text("Дата рождения (MM/DD/YYYY)") })
        OutlinedTextField(value = gender.value, onValueChange = { gender.value = it }, label = { Text("Пол (Мужской/Женский)") })

        Spacer(Modifier.height(12.dp))
        Button(onClick = onBack) { Text("Назад") }
        Spacer(Modifier.height(4.dp))
        Button(onClick = {
            when {
                lastName.value.isBlank() || firstName.value.isBlank() || birthday.value.isBlank() || gender.value.isBlank() ->
                    error.value = "Пожалуйста, заполните все обязательные поля."
                !BIRTHDAY_REGEX.matches(birthday.value) ->
                    error.value = "Введите корректную дату рождения."
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

private val BIRTHDAY_REGEX = Regex("""^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\d{4}$""")


