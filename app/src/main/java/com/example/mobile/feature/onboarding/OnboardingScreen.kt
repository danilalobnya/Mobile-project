package com.example.mobile.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import com.example.mobile.R


/**
 * Главный экран онбординга со слайдером
 */
@Composable
fun OnboardingSliderScreen(
    onSkip: () -> Unit,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    val pages = listOf(
        OnboardingPage(
            imageRes = R.drawable.on_boarding_image_1,
            title = "Аренда автомобилей",
            description = "Открой для себя удобный и доступный способ передвижения",
            buttonText = "Далее"
        ),
        OnboardingPage(
            imageRes = R.drawable.onboarding_image_2,
            title = "Безопасно и удобно",
            description = "Арендуй автомобиль и наслаждайся его удобством",
            buttonText = "Далее"
        ),
        OnboardingPage(
            imageRes = R.drawable.onboarding_image_3,
            title = "Лучшие предложения",
            description = "Выбирай понравившееся среди сотен доступных автомобилей",
            buttonText = "Поехали"
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // Кнопка Пропустить
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 40.dp, end = 24.dp)
                .zIndex(1f) // Убеждаемся, что кнопка поверх других элементов
        ) {
            TextButton(
                onClick = {
                    // Добавляем отладочную информацию
                    android.util.Log.d("Onboarding", "Skip button clicked")
                    onSkip()
                },
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.9f)) // Полупрозрачный фон для видимости
                    .padding(8.dp)
            ) {
                Text(
                    text = "Пропустить",
                    color = Color(0xFF1A0C2E),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        // HorizontalPager для слайдов
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingPageContent(
                pageData = pages[page],
                currentPage = page,
                onNext = {
                    if (page < 2) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page + 1)
                        }
                    } else {
                        onFinish()
                    }
                }
            )
        }

        // Индикаторы прогресса (внизу экрана)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp)
        ) {
            PageIndicators(
                currentPage = pagerState.currentPage,
                totalPages = 3
            )
        }
    }
}

/**
 * Данные для страницы онбординга
 */
data class OnboardingPage(
    val imageRes: Int,
    val title: String,
    val description: String,
    val buttonText: String
)

/**
 * Контент одной страницы онбординга
 */
@Composable
fun OnboardingPageContent(
    pageData: OnboardingPage,
    currentPage: Int,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    val horizontalPadding = if (isTablet) 48.dp else 24.dp
    val contentMaxWidth = if (isTablet) 400.dp else 342.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Реальное PNG изображение вместо заглушки
        Image(
            painter = painterResource(id = pageData.imageRes),
            contentDescription = pageData.title,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.55f)
                .padding(top = 80.dp),
            contentScale = ContentScale.Fit
        )

        // Гарантированный спейсер между изображением и текстом
        Spacer(modifier = Modifier.height(16.dp))

        // Текст и кнопки
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.45f)
                .padding(bottom = 40.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Текстовый контент
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = pageData.title,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = if (isTablet) 28.sp else 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = pageData.description,
                    color = Color(0xFF2A1246),
                    style = TextStyle(
                        fontSize = if (isTablet) 18.sp else 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 1.4.em
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Кнопка Далее/Поехали
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                NextButton(
                    text = pageData.buttonText,
                    onClick = onNext,
                    modifier = if (isTablet) Modifier.width(contentMaxWidth) else Modifier.fillMaxWidth(),
                    isTablet = isTablet
                )
            }
        }
    }
}

/**
 * Индикаторы страниц
 */
@Composable
fun PageIndicators(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        for (i in 0 until totalPages) {
            val isActive = i == currentPage
            val width = if (isActive) 40.dp else 16.dp
            val color = if (isActive) Color(0xff2a1246) else Color(0xffe4e7ec)

            Box(
                modifier = Modifier
                    .width(width)
                    .height(8.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(color = color)
            )
        }
    }
}

/**
 * Кнопка Далее/Поехали
 */
@Composable
fun NextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isTablet: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(56.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff2a1246)
        )
    ) {
        Text(
            text = text,
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
fun OnboardingSliderPreview() {
    Surface {
        OnboardingSliderScreen(
            onSkip = {},
            onFinish = {}
        )
    }
}

@Preview(widthDp = 768, heightDp = 1024)
@Composable
fun OnboardingSliderTabletPreview() {
    Surface {
        OnboardingSliderScreen(
            onSkip = {},
            onFinish = {}
        )
    }
}