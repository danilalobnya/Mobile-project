package com.example.mobile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * MobileApplication: главный класс приложения с поддержкой Hilt.
 * HiltAndroidApp аннотация генерирует код для инициализации Hilt.
 */
@HiltAndroidApp
class MobileApplication : Application()
