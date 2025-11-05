package com.example.mobile.feature.auth.login

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.R
import com.example.mobile.data.auth.AuthRepositoryFake

/**
 * LoginScreen: экран входа в аккаунт в стиле предыдущих страниц
 */
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit, // ИЗМЕНИТЬ: onBackToRegister -> onNavigateToRegister
    onLoginSuccess: (String, String) -> Unit,
    onForgotPassword: () -> Unit = {},
    vm: LoginViewModel = run {
        val repo = remember { AuthRepositoryFake() }
        viewModel(factory = LoginViewModel.Factory(repo))
    }
) {
    val ui by vm.uiState.collectAsState()
    val montserrat = FontFamily(
        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_semibold, FontWeight.SemiBold)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.width(342.dp)
        ) {
            // Заголовок
            Text(
                text = "Войдите в аккаунт",
                style = TextStyle(
                    fontFamily = montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color(0xFF1C1B1F),
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = "Пожалуйста, введите свои данные",
                style = TextStyle(
                    fontFamily = montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF7C7B80),
                    textAlign = TextAlign.Center
                )
            )

            Spacer(Modifier.height(16.dp))

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
                    value = ui.email,
                    onValueChange = vm::onEmailChanged,
                    placeholder = {
                        Text("Введите электронную почту", fontFamily = montserrat)
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Поле пароль
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Пароль",
                    style = TextStyle(
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color(0xFF1C1B1F)
                    )
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = ui.password,
                    onValueChange = vm::onPasswordChanged,
                    placeholder = { Text("Введите пароль", fontFamily = montserrat) },
                    singleLine = true,
                    visualTransformation = if (ui.passwordVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        val iconRes = if (ui.passwordVisible)
                            R.drawable.ic_eye_closed else R.drawable.ic_eye_open
                        Image(
                            painter = painterResource(iconRes),
                            contentDescription = if (ui.passwordVisible) "Скрыть пароль" else "Показать пароль",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { vm.togglePasswordVisibility() },
                            contentScale = ContentScale.Fit
                        )
                    }
                )
            }


            // Забыли пароль
            Text(
                text = "Забыли пароль?",
                style = TextStyle(
                    fontFamily = montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color(0xFF2A1246)
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable(onClick = onForgotPassword)
            )

            Spacer(Modifier.height(8.dp))

            // Кнопка "Войти"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2A1246))
                    .clickable(
                        enabled = ui.isFormValid && !ui.isLoading,
                        onClick = { vm.login(onLoginSuccess) }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (ui.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        "Войти",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    )
                }
            }

            // Кнопка Google
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFF2A1246), RoundedCornerShape(8.dp))
                    .clickable(enabled = !ui.isLoading) {
                        vm.loginWithGoogle(onLoginSuccess)
                    },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_google_logo_colored),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Войти через Google",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color(0xFF2A1246)
                        )
                    )
                }
            }

            // Регистрация
            Text(
                text = "Зарегистрироваться",
                style = TextStyle(
                    fontFamily = montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF2A1246)
                ),
                modifier = Modifier.clickable(onClick = onNavigateToRegister) // ИЗМЕНИТЬ: onBackToRegister -> onNavigateToRegister
            )
        }
    }

    if (ui.errorMessage != null) {
        AlertDialog(
            onDismissRequest = vm::dismissError,
            confirmButton = {
                TextButton(onClick = vm::dismissError) {
                    Text("Ок", fontFamily = montserrat)
                }
            },
            title = { Text("Ошибка", fontFamily = montserrat) },
            text = { Text(ui.errorMessage ?: "", fontFamily = montserrat) }
        )
    }
}
