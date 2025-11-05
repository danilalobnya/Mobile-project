package com.example.mobile.feature.settings

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mobile.R
import com.example.mobile.core.datastore.AppPreferences
import kotlinx.coroutines.launch
import androidx.compose.ui.draw.rotate
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onOpenProfile: () -> Unit,
    onLogout: () -> Unit,
    onOpenHelp: () -> Unit = {},
    onOpenMyBookings: () -> Unit = {}
) {
    val ctx = LocalContext.current
    val vm: SettingsViewModel = viewModel(factory = SettingsViewModel.factory(AppPreferences(ctx)))
    val scope = rememberCoroutineScope()

    val name by vm.userName.collectAsState(initial = "")
    val email by vm.userEmail.collectAsState(initial = "")
    val avatar by vm.userAvatar.collectAsState(initial = "")
    val notifications by vm.notifications.collectAsState(initial = true)
    val theme by vm.themeMode.collectAsState(initial = "system")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Настройки") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(painterResource(R.drawable.ic_back_arrow), contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ——— Профиль (как в макете) ———
            ListItem(
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(56.dp)                                   // размер кружка в настройках
                            .clip(CircleShape)
                            .background(Color(0xFFF5F5F5))                 // как на Step3
                            .border(2.dp, Color(0xFFE0E0E0), CircleShape), // как на Step3
                        contentAlignment = Alignment.Center
                    ) {
                        if (avatar.isNotEmpty()) {
                            AsyncImage(
                                model = avatar,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()                         // заполняем весь круг
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop           // обрезаем по кругу
                            )
                        } else {
                            Image(                                        // плейсхолдер внутри круга
                                painter = painterResource(R.drawable.profile_image),
                                contentDescription = null,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                },
                headlineContent = {
                    Text(text = if (name.isNotEmpty()) name else "Иван Иванов", fontWeight = FontWeight.SemiBold)
                },
                supportingContent = {
                    Text(text = if (email.isNotEmpty()) email else "ivan@mtuci.ru", style = MaterialTheme.typography.bodySmall)
                },
                trailingContent = {
                    Icon(
                        painterResource(R.drawable.ic_back_arrow), // развёрнутая «>» у тебя может не быть — временно используем эту
                        contentDescription = null,
                        modifier = Modifier.rotate(180f)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenProfile() }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
            SettingsRow(
                iconRes = R.drawable.ic_my_book_icon,
                title = "Мои бронирования",
                onClick = onOpenMyBookings
            )

            SettingsRow(
                iconRes = R.drawable.ic_light_mode_icon,
                title = "Тема",
                trailingText = when (theme) { "light" -> "Светлая"; "dark" -> "Тёмная"; else -> "Системная" },
                onClick = { /* ... */ }
            )

            SettingsRow(
                iconRes = R.drawable.ic_notif_icon,
                title = "Уведомления",
                trailingText = if (notifications) "Включены" else "Выключены",
                onClick = { /* ... */ }
            )

            SettingsRow(
                iconRes = R.drawable.ic_my_auto_icon,
                title = "Подключить свой автомобиль",
                onClick = { /* ... */ }
            )

            SettingsRow(
                iconRes = R.drawable.ic_help_icon,
                title = "Помощь",
                onClick = onOpenHelp
            )

            SettingsRow(
                iconRes = R.drawable.ic_email_icon,
                title = "Пригласи друга",
                onClick = { /* ... */ }
            )


            Spacer(Modifier.weight(1f))

            // ——— Выйти из профиля ———
            Button(
                onClick = { 
                    scope.launch { vm.logout() }
                    onLogout()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Text("Выйти из профиля")
            }
        }
    }
}

@Composable
private fun SettingsRow(
    @DrawableRes iconRes: Int,
    title: String,
    trailingText: String? = null,
    onClick: () -> Unit
) {
    ListItem(
        leadingContent = {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null
            )
        },
        headlineContent = { Text(title) },
        supportingContent = trailingText?.let {
            { Text(it, style = MaterialTheme.typography.bodySmall) }
        },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.ic_back_arrow), // временный chevron
                contentDescription = null,
                modifier = Modifier.rotate(180f)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

class SettingsViewModel(private val prefs: AppPreferences) : ViewModel() {
    val userName = prefs.userNameFlow
    val userEmail = prefs.userEmailFlow
    val userAvatar = prefs.userAvatarUriFlow

    val themeMode = prefs.themeModeFlow
    val notifications = prefs.notificationsEnabledFlow

    suspend fun setTheme(mode: String) = prefs.setThemeMode(mode)
    suspend fun setNotifications(enabled: Boolean) = prefs.setNotificationsEnabled(enabled)
    suspend fun logout() = prefs.logout()

    companion object {
        fun factory(prefs: AppPreferences) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                SettingsViewModel(prefs) as T
        }
    }
}
