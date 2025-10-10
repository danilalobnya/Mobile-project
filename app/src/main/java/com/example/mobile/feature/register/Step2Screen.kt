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
import androidx.compose.material3.RadioButton
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
 * Step2Screen: ФИО, дата рождения, пол в стиле первого экрана
 */
@Composable
fun Step2Screen(onNext: () -> Unit, onBack: () -> Unit) {
    val lastName = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val middleName = remember { mutableStateOf("") }
    val birthday = remember { mutableStateOf("") }
    val selectedGender = remember { mutableStateOf<String?>(null) }
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
                text = "Личные данные",
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
                // Фамилия
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Фамилия",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = lastName.value,
                        onValueChange = { lastName.value = it },
                        placeholder = {
                            Text("Введите фамилию", fontFamily = montserrat)
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Имя
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Имя",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = firstName.value,
                        onValueChange = { firstName.value = it },
                        placeholder = {
                            Text("Введите имя", fontFamily = montserrat)
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Отчество
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Отчество",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = middleName.value,
                        onValueChange = { middleName.value = it },
                        placeholder = {
                            Text("Введите отчество (необязательно)", fontFamily = montserrat)
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Дата рождения
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Дата рождения",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = birthday.value,
                        onValueChange = { text ->
                            // Упрощенное форматирование даты - пользователь сам вводит точки
                            birthday.value = text
                        },
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

                Spacer(Modifier.height(16.dp))

                // Пол
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Пол",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                    Spacer(Modifier.height(8.dp))

                    // Переключатель в виде радиокнопок как в макете
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { selectedGender.value = "male" }
                        ) {
                            androidx.compose.material3.RadioButton(
                                selected = selectedGender.value == "male",
                                onClick = { selectedGender.value = "male" },
                                colors = androidx.compose.material3.RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFF2A1246),
                                    unselectedColor = Color(0xFFD0D0D0)
                                )
                            )
                            Text(
                                text = "Мужской",
                                style = TextStyle(
                                    fontFamily = montserrat,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = Color(0xFF6D6A75)
                                )
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { selectedGender.value = "female" }
                        ) {
                            androidx.compose.material3.RadioButton(
                                selected = selectedGender.value == "female",
                                onClick = { selectedGender.value = "female" },
                                colors = androidx.compose.material3.RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFF2A1246),
                                    unselectedColor = Color(0xFFD0D0D0)
                                )
                            )
                            Text(
                                text = "Женский",
                                style = TextStyle(
                                    fontFamily = montserrat,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = Color(0xFF6D6A75)
                                )
                            )
                        }
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
                            enabled = isFormValid(lastName.value, firstName.value, birthday.value, selectedGender.value) && !isLoading.value,
                            onClick = {
                                isLoading.value = true
                                when {
                                    !isValidDate(birthday.value) -> {
                                        error.value = "Введите корректную дату рождения в формате DD.MM.YYYY"
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

private fun isFormValid(lastName: String, firstName: String, birthday: String, gender: String?): Boolean {
    return lastName.isNotBlank() && firstName.isNotBlank() && birthday.isNotBlank() && gender != null
}

private fun isValidDate(date: String): Boolean {
    return try {
        val regex = Regex("""^(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.\d{4}$""")
        regex.matches(date)
    } catch (e: Exception) {
        false
    }
}