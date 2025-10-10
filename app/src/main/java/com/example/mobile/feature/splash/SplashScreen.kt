package com.example.mobile.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mobile.R
import kotlinx.coroutines.delay

/**
 * SplashScreen: показывает логотип и слоган 2-3 секунды,
 * затем навигация на следующий экран в зависимости от состояния.
 */
@Composable
fun SplashScreen(
    onFinished: () -> Unit,
    // Позволяем параметризовать иллюстрацию из drawable
    illustrationResId: Int = R.drawable.ic_launcher_foreground
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onFinished()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Заголовок слева сверху
            Text(
                text = "DriveNext",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Поможем найти твою следующую поездку",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(32.dp))

            // Большая иллюстрация: тянем на ширину, сохраняем пропорции,
            // занимаем оставшееся пространство чтобы была крупной
            Image(
                painter = painterResource(id = illustrationResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}


