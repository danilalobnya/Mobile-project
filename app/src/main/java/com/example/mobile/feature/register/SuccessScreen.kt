package com.example.mobile.feature.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R

/**
 * Экран успешной регистрации
 * Отображается после завершения процесса регистрации
 */
@Composable
fun SuccessScreen(
    onGoHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    // Адаптивные размеры
    val horizontalPadding = if (isTablet) 80.dp else 24.dp
    val topPadding = if (isTablet) 80.dp else 64.dp
    val contentMaxWidth = if (isTablet) 400.dp else 342.dp
    val buttonBottomPadding = if (isTablet) 60.dp else 40.dp

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = horizontalPadding)
    ) {
        // Основной контент (текст и изображение) - центрирован по высоте
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SuccessContent(
                isTablet = isTablet,
                contentMaxWidth = contentMaxWidth
            )
        }

        // Заголовок "Пользуйтесь с удовольствием" - сверху
        Text(
            text = "Пользуйтесь с удовольствием",
            color = Color.Black,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = if (isTablet) 24.sp else 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = topPadding)
        )

        // Кнопка внизу экрана
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = buttonBottomPadding)
        ) {
            if (isTablet) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NextButtonSuccess(
                        onClick = onGoHome,
                        modifier = Modifier.width(contentMaxWidth),
                        isTablet = isTablet
                    )
                }
            } else {
                NextButtonSuccess(
                    onClick = onGoHome,
                    modifier = Modifier.fillMaxWidth(),
                    isTablet = isTablet
                )
            }
        }
    }
}

/**
 * Основной контент экрана успешной регистрации
 */
@Composable
private fun SuccessContent(
    isTablet: Boolean,
    contentMaxWidth: androidx.compose.ui.unit.Dp
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(if (isTablet) 32.dp else 24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Иконка успеха
        Image(
            painter = painterResource(id = R.drawable.registration_succes_image),
            contentDescription = "Успешная регистрация",
            modifier = Modifier
                .size(if (isTablet) 120.dp else 96.dp)
        )

        // Текстовый блок
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Поздравляем",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = if (isTablet) 28.sp else 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "Вы успешно зарегистрировались.\nОбработка ваших персональных данных займет около двух часов. Спасибо за ожидание.",
                color = Color(0xFF2A1246),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = if (isTablet) 16.sp else 14.sp,
                    lineHeight = if (isTablet) 24.sp else 20.sp
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Кнопка "Далее" для экрана успешной регистрации
 */
@Composable
private fun NextButtonSuccess(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isTablet: Boolean
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(if (isTablet) 56.dp else 52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2A1246)
        )
    ) {
        Text(
            text = "Далее",
            color = Color.White,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
fun SuccessScreenPhonePreview() {
    SuccessScreen(
        onGoHome = {}
    )
}

@Preview(widthDp = 768, heightDp = 1024)
@Composable
fun SuccessScreenTabletPreview() {
    SuccessScreen(
        onGoHome = {}
    )
}