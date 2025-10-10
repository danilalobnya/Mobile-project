package com.example.mobile.feature.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * OnboardingScreen: простой набор слайдов с кнопками "Пропустить", "Далее" и "Поехали".
 * Для простоты используем индекс текущего слайда без сложного пейджера.
 */
@Composable
fun OnboardingScreen(
    onSkip: () -> Unit,
    onFinished: () -> Unit
) {
    val totalSlides = 3
    val current = remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Слайд ${'$'}{current.intValue + 1}/${'$'}totalSlides", style = MaterialTheme.typography.titleLarge)
        Text(text = "Преимущество приложения #${'$'}{current.intValue + 1}")

        OutlinedButton(onClick = onSkip) { Text("Пропустить") }

        if (current.intValue < totalSlides - 1) {
            Button(onClick = { current.intValue++ }) { Text("Далее") }
        } else {
            Button(onClick = onFinished) { Text("Поехали") }
        }
    }
}


