package com.example.mobile.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * AuthChoiceScreen: выбор действия – "Войти" или "Зарегистрироваться".
 */
@Composable
fun AuthChoiceScreen(onLogin: () -> Unit, onRegister: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "DriveNext", style = MaterialTheme.typography.headlineLarge)
        Text(text = "Поможем найти твою следующую поездку", style = MaterialTheme.typography.bodyMedium)
        Button(onClick = onLogin) { Text("Войти") }
        Button(onClick = onRegister) { Text("Зарегистрироваться") }
    }
}


