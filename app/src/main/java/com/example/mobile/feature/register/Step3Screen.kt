package com.example.mobile.feature.register

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R

/**
 * Step3Screen: номер ВУ, дата выдачи, загрузка фото в стиле предыдущих экранов
 */
@Composable
fun Step3Screen(onNext: () -> Unit, onBack: () -> Unit) {
    val licenseNumber = remember { mutableStateOf("") }
    val issueDate = remember { mutableStateOf("") }
    val photosUploaded = remember { mutableStateOf(false) }
    val error: MutableState<String?> = remember { mutableStateOf(null) }
    val isLoading = remember { mutableStateOf(false) }

    val montserrat = FontFamily(
        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_semibold, FontWeight.SemiBold)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Верхняя панель с кнопкой назад и заголовком
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
        ) {
            // Кнопка назад вверху слева
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .clickable(onClick = onBack)
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(R.drawable.ic_back_arrow),
                    contentDescription = "Назад",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Заголовок вверху по центру
            Text(
                text = "Водительское удостоверение",
                style = TextStyle(
                    fontFamily = montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color(0xFF1C1B1F),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Основной контент
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Форма
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                // Номер водительского удостоверения
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Номер водительского удостоверения",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = licenseNumber.value,
                        onValueChange = { text ->
                            // Ограничение на 20 символов
                            if (text.length <= 20) {
                                licenseNumber.value = text
                            }
                        },
                        placeholder = {
                            Text("Введите номер ВУ", fontFamily = montserrat)
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Счетчик символов
                    Text(
                        text = "${licenseNumber.value.length}/20",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = if (licenseNumber.value.length == 20) Color(0xFFD32F2F) else Color(0xFF7C7B80)
                        ),
                        modifier = Modifier.align(Alignment.End)
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Дата выдачи
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Дата выдачи",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = issueDate.value,
                        onValueChange = { issueDate.value = it },
                        placeholder = {
                            Text("DD.MM.YYYY", fontFamily = montserrat)
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = {
                            androidx.compose.foundation.Image(
                                painter = painterResource(R.drawable.ic_calendar),
                                contentDescription = "Календарь",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { /* TODO: Открыть выбор даты */ },
                                contentScale = ContentScale.Fit
                            )
                        }
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Загрузка фото
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Загрузите фото документов",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                    Spacer(Modifier.height(12.dp))

                    // Кнопка загрузки фото
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF2A1246))
                            .clickable {
                                photosUploaded.value = true
                                // Заглушка - имитация загрузки
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Загрузить фото водительского и паспорта",
                            style = TextStyle(
                                fontFamily = montserrat,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    // Статус загрузки
                    if (photosUploaded.value) {
                        Text(
                            text = "✓ Фото успешно загружены",
                            style = TextStyle(
                                fontFamily = montserrat,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = Color(0xFF4CAF50)
                            )
                        )
                    } else {
                        Text(
                            text = "Необходимо загрузить фото водительского удостоверения и паспорта",
                            style = TextStyle(
                                fontFamily = montserrat,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = Color(0xFF7C7B80)
                            )
                        )
                    }
                }
            }

            // Нижняя часть с кнопкой
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 66.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Кнопка "Далее"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF2A1246))
                        .clickable(
                            enabled = isFormValid(licenseNumber.value, issueDate.value, photosUploaded.value) && !isLoading.value,
                            onClick = {
                                isLoading.value = true
                                when {
                                    !isValidLicenseNumber(licenseNumber.value) -> {
                                        error.value = "Введите корректный номер водительского удостоверения"
                                        isLoading.value = false
                                    }
                                    !isValidDate(issueDate.value) -> {
                                        error.value = "Введите корректную дату выдачи в формате DD.MM.YYYY"
                                        isLoading.value = false
                                    }
                                    !photosUploaded.value -> {
                                        error.value = "Пожалуйста, загрузите все необходимые фото"
                                        isLoading.value = false
                                    }
                                    else -> {
                                        // Имитация API запроса
                                        android.os.Handler().postDelayed({
                                            isLoading.value = false
                                            onNext()
                                        }, 1000)
                                    }
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            "Далее",
                            style = TextStyle(
                                fontFamily = montserrat,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }

    if (error.value != null) {
        AlertDialog(
            onDismissRequest = { error.value = null },
            confirmButton = {
                TextButton(onClick = { error.value = null }) {
                    Text("Ок", fontFamily = montserrat)
                }
            },
            title = { Text("Ошибка", fontFamily = montserrat) },
            text = { Text(error.value ?: "", fontFamily = montserrat) }
        )
    }
}

private fun isFormValid(licenseNumber: String, issueDate: String, photosUploaded: Boolean): Boolean {
    return licenseNumber.isNotBlank() && issueDate.isNotBlank() && photosUploaded
}

private fun isValidDate(date: String): Boolean {
    return try {
        val regex = Regex("""^(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.\d{4}$""")
        regex.matches(date)
    } catch (e: Exception) {
        false
    }
}

private fun isValidLicenseNumber(licenseNumber: String): Boolean {
    // Простая валидация: номер должен содержать только буквы и цифры, длиной от 5 до 20 символов
    return licenseNumber.matches(Regex("^[A-Za-z0-9]{5,20}$"))
}