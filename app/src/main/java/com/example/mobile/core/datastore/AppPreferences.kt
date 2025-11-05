package com.example.mobile.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Общий DataStore для всего приложения
private val Context.dataStore by preferencesDataStore(name = "app_prefs")

/**
 * AppPreferences: простой слой хранения флагов и токена в DataStore.
 * Сохраняем: прошел ли пользователь онбординг, access-токен, профиль пользователя,
 * настройки темы и уведомлений.
 */
class AppPreferences(private val context: Context) {

    private object Keys {
        val onboardingCompleted: Preferences.Key<Boolean> = booleanPreferencesKey("onboarding_completed")
        val accessToken: Preferences.Key<String> = stringPreferencesKey("access_token")
        
        // Профиль пользователя
        val userName: Preferences.Key<String> = stringPreferencesKey("user_name")
        val userEmail: Preferences.Key<String> = stringPreferencesKey("user_email")
        val userAvatarUri: Preferences.Key<String> = stringPreferencesKey("user_avatar_uri")
        
        // Настройки
        val themeMode: Preferences.Key<String> = stringPreferencesKey("theme_mode") // "light", "dark", "system"
        val notificationsEnabled: Preferences.Key<Boolean> = booleanPreferencesKey("notifications_enabled")
    }

    // Потоки для онбординга и токена
    val onboardingCompletedFlow: Flow<Boolean> = context.dataStore.data
        .map { it[Keys.onboardingCompleted] ?: false }

    val accessTokenFlow: Flow<String?> = context.dataStore.data
        .map { it[Keys.accessToken] }

    // Потоки для профиля пользователя
    val userNameFlow: Flow<String> = context.dataStore.data
        .map { it[Keys.userName] ?: "" }
    
    val userEmailFlow: Flow<String> = context.dataStore.data
        .map { it[Keys.userEmail] ?: "" }
    
    val userAvatarUriFlow: Flow<String> = context.dataStore.data
        .map { it[Keys.userAvatarUri] ?: "" }

    // Потоки для настроек
    val themeModeFlow: Flow<String> = context.dataStore.data
        .map { it[Keys.themeMode] ?: "system" }
    
    val notificationsEnabledFlow: Flow<Boolean> = context.dataStore.data
        .map { it[Keys.notificationsEnabled] ?: true }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.onboardingCompleted] = completed
        }
    }

    suspend fun setAccessToken(token: String?) {
        context.dataStore.edit { prefs ->
            if (token.isNullOrEmpty()) {
                prefs.remove(Keys.accessToken)
            } else {
                prefs[Keys.accessToken] = token
            }
        }
    }
    
    // Методы для профиля пользователя
    suspend fun setUserProfile(name: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.userName] = name
            prefs[Keys.userEmail] = email
        }
    }
    
    suspend fun setUserAvatarUri(uri: String?) {
        context.dataStore.edit { prefs ->
            if (uri.isNullOrEmpty()) {
                prefs.remove(Keys.userAvatarUri)
            } else {
                prefs[Keys.userAvatarUri] = uri
            }
        }
    }
    
    // Методы для настроек
    suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.themeMode] = mode
        }
    }
    
    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.notificationsEnabled] = enabled
        }
    }
    
    // Выход из профиля (очищаем токен и данные профиля)
    suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs.remove(Keys.accessToken)
            prefs.remove(Keys.userName)
            prefs.remove(Keys.userEmail)
            prefs.remove(Keys.userAvatarUri)
            // Настройки темы и уведомлений сохраняем
        }
    }
}


