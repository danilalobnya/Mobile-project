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
 * Step3Screen: номер ВУ, дата выдачи (DD/MM/YYYY), кнопки загрузки фото (заглушки).
 */
@Composable
fun Step3Screen(onNext: () -> Unit, onBack: () -> Unit) {
    val licenseNumber = remember { mutableStateOf("") }
    val issueDate = remember { mutableStateOf("") }
    val photosUploaded = remember { mutableStateOf(false) }
    val error: MutableState<String?> = remember { mutableStateOf(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Регистрация – Шаг 3", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = licenseNumber.value, onValueChange = { licenseNumber.value = it }, label = { Text("Номер водительского удостоверения") })
        OutlinedTextField(value = issueDate.value, onValueChange = { issueDate.value = it }, label = { Text("Дата выдачи (DD/MM/YYYY)") })
        Spacer(Modifier.height(8.dp))
        Button(onClick = { photosUploaded.value = true }) { Text("Загрузить фото водительского и паспорта (заглушка)") }

        Spacer(Modifier.height(12.dp))
        Button(onClick = onBack) { Text("Назад") }
        Spacer(Modifier.height(4.dp))
        Button(onClick = {
            when {
                licenseNumber.value.isBlank() || issueDate.value.isBlank() ->
                    error.value = "Пожалуйста, заполните все обязательные поля."
                !ISSUE_DATE_REGEX.matches(issueDate.value) ->
                    error.value = "Введите корректную дату выдачи."
                !photosUploaded.value ->
                    error.value = "Пожалуйста, загрузите все необходимые фото."
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

private val ISSUE_DATE_REGEX = Regex("""^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\d{4}$""")


