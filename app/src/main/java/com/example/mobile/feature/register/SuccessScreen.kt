package com.example.mobile.feature.register

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
 * SuccessScreen: поздравление и кнопка перейти на главный экран.
 */
@Composable
fun SuccessScreen(onGoHome: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Успешная регистрация!", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onGoHome) { Text("Далее") }
    }
}


