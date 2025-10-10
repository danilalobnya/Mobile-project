package com.example.mobile.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * HomeScreen: простая заглушка главного экрана с кнопкой сброса состояния
 * (удаляет токен и флаг онбординга для повторного входа/регистрации).
 */
@Composable
fun HomeScreen(onResetAppState: (() -> Unit)? = null) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Главный экран", style = MaterialTheme.typography.titleLarge)
            if (onResetAppState != null) {
                Button(onClick = onResetAppState) { Text("Сбросить сессию и онбординг") }
            }
        }
    }
}


