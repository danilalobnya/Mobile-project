package com.example.mobile.feature.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import com.example.mobile.R

/**
 * AuthChoiceScreen: выбор действия – "Войти" или "Зарегистрироваться".
 */
@Composable
fun AuthChoiceScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    // ID ресурса для изображения
    illustrationResId: Int = R.drawable.splash_image
) {
    // Шрифты Montserrat
    val montserratFontFamily = FontFamily(
        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_semibold, FontWeight.SemiBold)
    )

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
                style = TextStyle(
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Поможем найти твою следующую поездку",
                style = TextStyle(
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(Modifier.height(32.dp))

            // Большая иллюстрация: тянем на ширину, сохраняем пропорции,
            // занимаем оставшееся пространство чтобы была крупной
            // ТОЧНО ТАК ЖЕ КАК В SPLASH SCREEN
            Image(
                painter = painterResource(id = illustrationResId),
                contentDescription = "DriveNext иллюстрация",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.FillWidth
            )
        }

        // Кнопки внизу экрана (абсолютное позиционирование)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 66.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Основная кнопка "Войти"
            Box(
                modifier = Modifier
                    .width(342.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2A1246))
                    .clickable(onClick = onLogin),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Войти",
                    style = TextStyle(
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.sp,
                        color = Color.White
                    )
                )
            }

            // Контрастная кнопка "Зарегистрироваться" (с белым фоном и фиолетовой обводкой)
            Box(
                modifier = Modifier
                    .width(342.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(
                        width = 2.dp,
                        color = Color(0xFF2A1246),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(onClick = onRegister),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Зарегистрироваться",
                    style = TextStyle(
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.sp,
                        color = Color(0xFF2A1246)
                    )
                )
            }
        }
    }
}