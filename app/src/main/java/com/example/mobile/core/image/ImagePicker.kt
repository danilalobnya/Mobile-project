package com.example.mobile.core.image

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * ImagePicker: Утилита для выбора изображений из галереи или камеры.
 * Предоставляет launcher'ы для работы с изображениями.
 */
class ImagePicker {
    
    /**
     * Создает launcher для выбора изображения из галереи.
     * @param onImageSelected Callback, вызываемый при выборе изображения.
     * @return ActivityResultLauncher для запуска выбора изображения.
     */
    @Composable
    fun rememberGalleryLauncher(onImageSelected: (Uri?) -> Unit): ManagedActivityResultLauncher<String, Uri?> {
        return rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            onImageSelected(uri)
        }
    }
    
    /**
     * Создает launcher для съемки фото с камеры.
     * @param onImageSelected Callback, вызываемый при съемке фото.
     * @return ActivityResultLauncher для запуска камеры.
     */
    @Composable
    fun rememberCameraLauncher(onImageSelected: (Uri?) -> Unit): ManagedActivityResultLauncher<Uri, Boolean> {
        return rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success: Boolean ->
            if (success) {
                // В реальном приложении здесь нужно сохранить URI временного файла
                // Для демонстрации просто вызываем callback
                onImageSelected(null)
            }
        }
    }
}

/**
 * Composable функция для создания ImagePicker.
 */
@Composable
fun rememberImagePicker(): ImagePicker {
    return remember { ImagePicker() }
}
