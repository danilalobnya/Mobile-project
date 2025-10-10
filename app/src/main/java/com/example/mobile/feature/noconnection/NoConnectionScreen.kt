package com.example.mobile.feature.noconnection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@Composable
fun NoConnectionScreen(
    onRetry: () -> Unit,
    noConnectionImageResId: Int = R.drawable.no_connection_image
) {
    // Определяем FontFamily с шрифтами из папки res/font
    val montserratFontFamily = FontFamily(
        // Для обычного веса (400)
        Font(R.font.montserrat_regular, FontWeight.Normal),
        // Для полужирного веса (600)
        Font(R.font.montserrat_semibold, FontWeight.SemiBold)
    )

    // Стили текста с использованием Montserrat
    val titleStyle = TextStyle(
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.SemiBold, // 600
        fontSize = 24.sp,
        lineHeight = 24.sp,
        textAlign = TextAlign.Center,
        letterSpacing = 0.sp
    )

    val bodyStyle = TextStyle(
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.Normal, // 400
        fontSize = 14.sp,
        lineHeight = 14.sp,
        textAlign = TextAlign.Center,
        letterSpacing = 0.sp
    )

    val buttonTextStyle = TextStyle(
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.SemiBold, // 600
        fontSize = 16.sp,
        lineHeight = 16.sp,
        textAlign = TextAlign.Center,
        letterSpacing = 0.sp
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Центральная часть с контентом об ошибке
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = noConnectionImageResId),
                contentDescription = "Нет подключения к интернету",
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Нет подключения к интернету",
                style = titleStyle,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Проверьте подключение и повторите попытку",
                style = bodyStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Кнопка повтора
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 66.dp)
                .width(342.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF2A1246))
                .clickable(onClick = onRetry),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Повторить попытку",
                style = buttonTextStyle,
                color = Color.White
            )
        }
    }
}