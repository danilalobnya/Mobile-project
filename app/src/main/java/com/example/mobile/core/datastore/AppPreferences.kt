package com.example.mobile.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * AppPreferences: простой слой хранения флагов и токена в DataStore.
 * Сохраняем: прошел ли пользователь онбординг и access-токен.
 */
class AppPreferences(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "app_prefs")

    private object Keys {
        val onboardingCompleted: Preferences.Key<Boolean> = booleanPreferencesKey("onboarding_completed")
        val accessToken: Preferences.Key<String> = stringPreferencesKey("access_token")
    }

    val onboardingCompletedFlow: Flow<Boolean> = context.dataStore.data
        .map { it[Keys.onboardingCompleted] ?: false }

    val accessTokenFlow: Flow<String?> = context.dataStore.data
        .map { it[Keys.accessToken] }

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
}


