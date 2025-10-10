package com.example.mobile.feature.noconnection

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
 * NoConnectionScreen: экран отсутствия интернета с кнопкой "Повторить попытку".
 */
@Composable
fun NoConnectionScreen(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Нет подключения к интернету", style = MaterialTheme.typography.titleLarge)
        Text(text = "Проверьте подключение и повторите попытку", style = MaterialTheme.typography.bodyMedium)
        Button(onClick = onRetry) {
            Text("Повторить попытку")
        }
    }
}


