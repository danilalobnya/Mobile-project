package com.example.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atmose.drivenext.presentation.screens.onboarding.OnboardingSliderScreen
import com.example.mobile.core.connectivity.ConnectivityObserver
import com.example.mobile.core.datastore.AppPreferences
import com.example.mobile.feature.noconnection.NoConnectionScreen
import com.example.mobile.feature.splash.SplashScreen
import com.example.mobile.feature.auth.AuthChoiceScreen
import com.example.mobile.feature.auth.login.LoginScreen
import com.example.mobile.feature.register.Step1Screen
import com.example.mobile.feature.register.Step2Screen
import com.example.mobile.feature.register.Step3Screen
import com.atmose.drivenext.presentation.screens.register.SuccessScreen
import com.example.mobile.feature.home.HomeScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * MainActivity: точка входа в приложение.
 * Здесь настраиваем Compose и подключаем навигацию между экранами.
 */
class MainActivity : ComponentActivity() {
    lateinit var connectivityObserver: ConnectivityObserver
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = ConnectivityObserver(this)
        appPreferences = AppPreferences(this)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    onDecideStart = { decideStartDestination(navController) },
                    onCompleteOnboarding = { completeOnboardingAndGoToAuth(navController) },
                    onLoginSuccess = { token -> onLoginSuccess(navController, token) }
                )
            }
        }
    }
}

/**
 * AppNavHost: определяет граф навигации.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    onDecideStart: () -> Unit,
    onCompleteOnboarding: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onFinished = onDecideStart, illustrationResId = R.drawable.splash_image)
        }
        composable("no_connection") {
            NoConnectionScreen(onRetry = onDecideStart)
        }
        composable("onboarding") {
            OnboardingSliderScreen(
                onSkip = {
                    // Пропустить онбординг и перейти к авторизации
                    onCompleteOnboarding()
                },
                onFinish = {
                    // Завершить онбординг и перейти к авторизации
                    onCompleteOnboarding()
                }
            )
        }
        composable("auth_choice") {
            AuthChoiceScreen(
                onLogin = { navController.navigate("login") },
                onRegister = { navController.navigate("register_step1") }
            )
        }
        composable("login") {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate("register_step1") // ИЗМЕНИТЬ: переход на регистрацию
                },
                onLoginSuccess = { token -> onLoginSuccess(token) },
                onForgotPassword = { /* навигация к восстановлению пароля */ }
            )
        }
        composable("register_step1") {
            Step1Screen(
                onNext = { navController.navigate("register_step2") },
                onBack = { navController.popBackStack() },
                onNavigateToLogin = {
                    navController.navigate("login") { // ДОБАВИТЬ: переход на логин
                        popUpTo("register_step1") { inclusive = true }
                    }
                }
            )
        }

        composable("register_step2") {
            Step2Screen(
                onNext = { navController.navigate("register_step3") },
                onBack = { navController.popBackStack() }
            )
        }

        composable("register_step3") {
            Step3Screen(
                onNext = { navController.navigate("register_success") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("register_success") {
            SuccessScreen(onGoHome = {
                navController.navigate("home") {
                    popUpTo("auth_choice") { inclusive = true }
                }
            })
        }
        composable("home") {
            HomeScreen(onResetAppState = {
                // Очистим хранилище и вернёмся на сплэш, как при первом запуске
                (navController.context as MainActivity).resetAppAndRestart(navController)
            })
        }
    }
}

/**
 * CenterTextScreen: простой плейсхолдер экрана с текстом по центру.
 */
@Composable
fun CenterTextScreen(label: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = label, style = MaterialTheme.typography.titleLarge)
    }
}

/**
 * decideStartDestination: простая проверка подключения и состояния данных,
 * чтобы определить куда перейти после Splash/Retry.
 * Согласно требованиям лабораторной работы:
 * - Если пользователь открывает приложение первый раз или access токен недействителен -> onboarding или auth
 * - Если есть валидный токен -> home
 */
private fun MainActivity.decideStartDestination(navController: NavHostController) {
    lifecycleScope.launch {
        val isConnected = connectivityObserver.observe().first()
        if (!isConnected) {
            navController.navigate("no_connection") {
                popUpTo("splash") { inclusive = true }
            }
            return@launch
        }

        val token = appPreferences.accessTokenFlow.first()
        val onboardingDone = appPreferences.onboardingCompletedFlow.first()

        val next = when {
            token?.isNotEmpty() == true -> "home" // Валидный токен - на главный экран
            onboardingDone -> "auth_choice" // Онбординг пройден - выбор входа/регистрации
            else -> "onboarding" // Первый запуск - показать онбординг
        }

        navController.navigate(next) {
            popUpTo("splash") { inclusive = true }
        }
    }
}

/**
 * Помощник: отметить прохождение онбординга и перейти к выбору входа/регистрации.
 * Согласно требованиям: после онбординга информация сохраняется, чтобы не показывать повторно.
 */
private fun MainActivity.completeOnboardingAndGoToAuth(navController: NavHostController) {
    lifecycleScope.launch {
        appPreferences.setOnboardingCompleted(true)
        navController.navigate("auth_choice") {
            popUpTo("onboarding") { inclusive = true }
        }
    }
}

/**
 * Обработка успешного входа: сохранить токен и перейти на главный экран.
 * Согласно требованиям: сохранить данные входа и активную сессию.
 */
private fun MainActivity.onLoginSuccess(navController: NavHostController, token: String) {
    lifecycleScope.launch {
        appPreferences.setAccessToken(token)
        navController.navigate("home") {
            popUpTo("auth_choice") { inclusive = true }
        }
    }
}

/**
 * Полный сброс состояния приложения: удаляем токен и флаг онбординга,
 * затем переходим на сплэш, чтобы пройти путь как при первом запуске.
 */
private fun MainActivity.resetAppAndRestart(navController: NavHostController) {
    lifecycleScope.launch {
        appPreferences.setAccessToken(null)
        appPreferences.setOnboardingCompleted(false)
        navController.navigate("splash") {
            popUpTo(0) { inclusive = true }
        }
    }
}

@Preview
@Composable
private fun PreviewRoot() {
    Surface(color = MaterialTheme.colorScheme.background) {
        CenterTextScreen("Preview")
    }
}