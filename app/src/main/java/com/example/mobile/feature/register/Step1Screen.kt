package com.example.mobile.feature.register

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.example.mobile.R
import com.example.mobile.core.datastore.AppPreferences

/**
 * Step1Screen: экран регистрации в стиле экрана входа
 */
@Composable
fun Step1Screen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    onNavigateToLogin: () -> Unit = {} // ДОБАВИТЬ: параметр для навигации на логин
) {
    val ctx = LocalContext.current
    val prefs = AppPreferences(ctx)
    val scope = rememberCoroutineScope()
    
    val email = remember { mutableStateOf("") }
    val pass = remember { mutableStateOf("") }
    val pass2 = remember { mutableStateOf("") }
    val showPass = remember { mutableStateOf(false) }
    val showPass2 = remember { mutableStateOf(false) }
    val agreed = remember { mutableStateOf(false) }
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
            // Кнопка назад вверху слева (SVG иконка 24x24)
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
                text = "Создать аккаунт",
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
                .padding(top = 56.dp), // Отступ под верхнюю панель
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Форма - УВЕЛИЧИВАЕМ ОТСТУП СВЕРХУ ДЛЯ ПОЛЕЙ ВВОДА
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 80.dp), // УВЕЛИЧИЛИ ОТСТУП до 80dp (было 40dp)
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                // Поле email
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Электронная почта",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        placeholder = {
                            Text("Введите электронную почту", fontFamily = montserrat)
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(24.dp)) // Увеличиваем расстояние между полями

                // Поле пароль
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Придумайте пароль",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = pass.value,
                        onValueChange = { pass.value = it },
                        placeholder = { Text("Введите пароль", fontFamily = montserrat) },
                        singleLine = true,
                        visualTransformation = if (showPass.value)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            val iconRes = if (showPass.value)
                                R.drawable.ic_eye_closed else R.drawable.ic_eye_open
                            Image(
                                painter = painterResource(iconRes),
                                contentDescription = if (showPass.value) "Скрыть пароль" else "Показать пароль",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { showPass.value = !showPass.value },
                                contentScale = ContentScale.Fit
                            )
                        }
                    )
                }

                Spacer(Modifier.height(24.dp)) // Увеличиваем расстояние между полями

                // Поле подтверждения пароля
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Повторите пароль",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = pass2.value,
                        onValueChange = { pass2.value = it },
                        placeholder = { Text("Повторите пароль", fontFamily = montserrat) },
                        singleLine = true,
                        visualTransformation = if (showPass2.value)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            val iconRes = if (showPass2.value)
                                R.drawable.ic_eye_closed else R.drawable.ic_eye_open
                            Image(
                                painter = painterResource(iconRes),
                                contentDescription = if (showPass2.value) "Скрыть пароль" else "Показать пароль",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { showPass2.value = !showPass2.value },
                                contentScale = ContentScale.Fit
                            )
                        }
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Соглашение (точно такое же выравнивание как у полей ввода)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 0.dp), // Убираем все лишние отступы
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = agreed.value,
                        onCheckedChange = { agreed.value = it },
                        modifier = Modifier.padding(start = 0.dp) // Галочка точно у левого края
                    )
                    Text(
                        text = "Я согласен с условиями обслуживания и политикой конфиденциальности",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Нижняя часть с кнопкой и ссылкой
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 66.dp), // Опускаем кнопку вниз
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
                            enabled = isFormValid(email.value, pass.value, pass2.value, agreed.value) && !isLoading.value,
                            onClick = {
                                isLoading.value = true
                                // Имитация загрузки
                                when {
                                    !EMAIL_REGEX.toRegex().matches(email.value) -> {
                                        error.value = "Введите корректный адрес электронной почты."
                                        isLoading.value = false
                                    }
                                    pass.value != pass2.value -> {
                                        error.value = "Пароли не совпадают."
                                        isLoading.value = false
                                    }
                                    !agreed.value -> {
                                        error.value = "Необходимо согласиться с условиями обслуживания и политикой конфиденциальности."
                                        isLoading.value = false
                                    }
                                    else -> {
                                        // Сохраняем email перед переходом на следующий шаг
                                        scope.launch {
                                            prefs.setUserProfile("", email.value) // Имя пустое, сохраним на следующем шаге
                                        }
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

                // Вход
                Text(
                    text = "Уже есть аккаунт? Войти",
                    style = TextStyle(
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color(0xFF2A1246)
                    ),
                    modifier = Modifier.clickable(onClick = onNavigateToLogin) // ИЗМЕНИТЬ: onLogin -> onNavigateToLogin
                )
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

private fun isFormValid(email: String, pass: String, pass2: String, agreed: Boolean): Boolean {
    return email.isNotBlank() && pass.isNotBlank() && pass2.isNotBlank() && agreed
}

private const val EMAIL_REGEX = ".+@.+\\..+"