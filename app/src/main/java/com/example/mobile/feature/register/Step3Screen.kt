package com.example.mobile.feature.register

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R
import com.example.mobile.core.image.ImagePicker
import com.example.mobile.core.image.ImageSourceDialog
import com.example.mobile.core.image.rememberImagePicker

/**
 * Step3Screen: номер ВУ, дата выдачи, загрузка фото в стиле скриншота
 */
@Composable
fun Step3Screen(onNext: () -> Unit, onBack: () -> Unit) {
    val licenseNumber = remember { mutableStateOf("") }
    val issueDate = remember { mutableStateOf("") }
    val licensePhotoUploaded = remember { mutableStateOf(false) }
    val passportPhotoUploaded = remember { mutableStateOf(false) }
    val profilePhotoUri = remember { mutableStateOf<Uri?>(null) }
    val error: MutableState<String?> = remember { mutableStateOf(null) }
    val isLoading = remember { mutableStateOf(false) }
    val showImageSourceDialog = remember { mutableStateOf(false) }
    val showLicenseImageSourceDialog = remember { mutableStateOf(false) }
    val showPassportImageSourceDialog = remember { mutableStateOf(false) }
    
    val imagePicker = rememberImagePicker()
    
    // Launcher'ы для выбора изображений
    val profileGalleryLauncher = imagePicker.rememberGalleryLauncher { uri ->
        profilePhotoUri.value = uri
        showImageSourceDialog.value = false
    }
    
    val profileCameraLauncher = imagePicker.rememberCameraLauncher { uri ->
        profilePhotoUri.value = uri
        showImageSourceDialog.value = false
    }
    
    val licenseGalleryLauncher = imagePicker.rememberGalleryLauncher { uri ->
        licensePhotoUploaded.value = true
        showLicenseImageSourceDialog.value = false
    }
    
    val licenseCameraLauncher = imagePicker.rememberCameraLauncher { uri ->
        licensePhotoUploaded.value = true
        showLicenseImageSourceDialog.value = false
    }
    
    val passportGalleryLauncher = imagePicker.rememberGalleryLauncher { uri ->
        passportPhotoUploaded.value = true
        showPassportImageSourceDialog.value = false
    }
    
    val passportCameraLauncher = imagePicker.rememberCameraLauncher { uri ->
        passportPhotoUploaded.value = true
        showPassportImageSourceDialog.value = false
    }

    val montserrat = FontFamily(
        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_semibold, FontWeight.SemiBold)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Верхняя панель с кнопкой назад и заголовком
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
        ) {
            // Кнопка назад вверху слева
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .clickable(onClick = onBack)
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(R.drawable.ic_back_arrow),
                    contentDescription = "Назад",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Заголовок вверху по центру
            Text(
                text = "Создать аккаунт",
                style = TextStyle(
                    fontFamily = montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color(0xFF1C1B1F),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Основной контент
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Иконка загрузки фотографии профиля (128x128)
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .padding(top = 24.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = Color(0xFFE0E0E0),
                        shape = CircleShape
                    )
                    .clickable { showImageSourceDialog.value = true }
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(R.drawable.icon_profile),
                    contentDescription = "Загрузка фото",
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Fit
                )
            }

            // Описание
            Text(
                text = "Добавление фотографии поможет владельцам и арендаторам узнать друг друга, когда они будут забирать машину",
                style = TextStyle(
                    fontFamily = montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF7C7B80),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 32.dp)
            )

            // Форма
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                // Номер водительского удостоверения
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Номер водительского удостоверения",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = licenseNumber.value,
                        onValueChange = { text ->
                            // Ограничение на 10 цифр
                            if (text.length <= 10 && text.all { it.isDigit() }) {
                                licenseNumber.value = text
                            }
                        },
                        placeholder = {
                            Text(
                                "0000000000",
                                fontFamily = montserrat,
                                color = Color(0xFF7C7B80)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(
                            fontFamily = montserrat,
                            fontSize = 16.sp,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Дата выдачи
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Дата выдачи",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = issueDate.value,
                        onValueChange = { text ->
                            // Пользователь сам ставит точки, просто проверяем длину
                            if (text.length <= 10) {
                                issueDate.value = text
                            }
                        },
                        placeholder = {
                            Text(
                                "12.10.2005",
                                fontFamily = montserrat,
                                color = Color(0xFF7C7B80)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(
                            fontFamily = montserrat,
                            fontSize = 16.sp,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                }

                Spacer(Modifier.height(32.dp))

                // Загрузка фото водительского удостоверения
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Загрузите фото водительского удостоверения",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Кнопка загрузки фото ВУ
                    UploadPhotoButton(
                        text = "Загрузить фото",
                        isUploaded = licensePhotoUploaded.value,
                        onUploadClick = { showLicenseImageSourceDialog.value = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Загрузка фото паспорта
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Загрузите фото паспорта",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1C1B1F)
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Кнопка загрузки фото паспорта
                    UploadPhotoButton(
                        text = "Загрузить фото",
                        isUploaded = passportPhotoUploaded.value,
                        onUploadClick = { showPassportImageSourceDialog.value = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Нижняя часть с кнопкой
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Кнопка "Далее"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isFormValid(
                                    licenseNumber.value,
                                    issueDate.value,
                                    licensePhotoUploaded.value,
                                    passportPhotoUploaded.value,
                                    profilePhotoUri.value != null
                                ) && !isLoading.value
                            ) Color(0xFF2A1246) else Color(0xFFE0E0E0)
                        )
                        .clickable(
                            enabled = isFormValid(
                                licenseNumber.value,
                                issueDate.value,
                                licensePhotoUploaded.value,
                                passportPhotoUploaded.value,
                                profilePhotoUri.value != null
                            ) && !isLoading.value,
                            onClick = {
                                isLoading.value = true
                                when {
                                    !isValidLicenseNumber(licenseNumber.value) -> {
                                        error.value = "Введите корректный номер водительского удостоверения (10 цифр)"
                                        isLoading.value = false
                                    }
                                    !isValidDate(issueDate.value) -> {
                                        error.value = "Введите корректную дату выдачи в формате DD.MM.YYYY"
                                        isLoading.value = false
                                    }
                                    !licensePhotoUploaded.value -> {
                                        error.value = "Пожалуйста, загрузите фото водительского удостоверения"
                                        isLoading.value = false
                                    }
                                    !passportPhotoUploaded.value -> {
                                        error.value = "Пожалуйста, загрузите фото паспорта"
                                        isLoading.value = false
                                    }
                                    profilePhotoUri.value == null -> {
                                        error.value = "Пожалуйста, добавьте фотографию профиля"
                                        isLoading.value = false
                                    }
                                    else -> {
                                        // Имитация API запроса
                                        android.os.Handler().postDelayed({
                                            isLoading.value = false
                                            onNext()
                                        }, 1000)
                                    }
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            "Далее",
                            style = TextStyle(
                                fontFamily = montserrat,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = if (isFormValid(
                                        licenseNumber.value,
                                        issueDate.value,
                                        licensePhotoUploaded.value,
                                        passportPhotoUploaded.value,
                                        profilePhotoUri.value != null
                                    )
                                ) Color.White else Color(0xFF9E9E9E)
                            )
                        )
                    }
                }
            }
        }
    }

    // Диалог ошибки
    if (error.value != null) {
        AlertDialog(
            onDismissRequest = { error.value = null },
            confirmButton = {
                TextButton(onClick = { error.value = null }) {
                    Text("Ок", fontFamily = montserrat)
                }
            },
            title = { Text("Ошибка", fontFamily = montserrat) },
            text = { Text(error.value ?: "", fontFamily = montserrat) }
        )
    }
    
    // Диалог выбора источника для фото профиля
    if (showImageSourceDialog.value) {
        ImageSourceDialog(
            onDismiss = { showImageSourceDialog.value = false },
            onGallerySelected = { 
                profileGalleryLauncher.launch("image/*")
            },
            onCameraSelected = { 
                // Для демонстрации используем заглушку
                // В реальном приложении здесь нужно создать временный файл
                profilePhotoUri.value = android.net.Uri.parse("content://demo")
                showImageSourceDialog.value = false
            }
        )
    }
    
    // Диалог выбора источника для фото водительского удостоверения
    if (showLicenseImageSourceDialog.value) {
        ImageSourceDialog(
            onDismiss = { showLicenseImageSourceDialog.value = false },
            onGallerySelected = { 
                licenseGalleryLauncher.launch("image/*")
            },
            onCameraSelected = { 
                // Для демонстрации используем заглушку
                licensePhotoUploaded.value = true
                showLicenseImageSourceDialog.value = false
            }
        )
    }
    
    // Диалог выбора источника для фото паспорта
    if (showPassportImageSourceDialog.value) {
        ImageSourceDialog(
            onDismiss = { showPassportImageSourceDialog.value = false },
            onGallerySelected = { 
                passportGalleryLauncher.launch("image/*")
            },
            onCameraSelected = { 
                // Для демонстрации используем заглушку
                passportPhotoUploaded.value = true
                showPassportImageSourceDialog.value = false
            }
        )
    }
}

@Composable
fun UploadPhotoButton(
    text: String,
    isUploaded: Boolean,
    onUploadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val montserrat = FontFamily(
        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_semibold, FontWeight.SemiBold)
    )

    Box(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = if (isUploaded) Color(0xFF4CAF50) else Color(0xFF7C7B80),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onUploadClick)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isUploaded) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Загружено",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
            }
            Text(
                text = if (isUploaded) "Фото загружено" else text,
                style = TextStyle(
                    fontFamily = montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = if (isUploaded) Color(0xFF4CAF50) else Color(0xFF1C1B1F)
                )
            )
        }
    }
}

private fun isFormValid(
    licenseNumber: String,
    issueDate: String,
    licensePhotoUploaded: Boolean,
    passportPhotoUploaded: Boolean,
    profilePhotoSelected: Boolean
): Boolean {
    return licenseNumber.isNotBlank() &&
            issueDate.isNotBlank() &&
            licensePhotoUploaded &&
            passportPhotoUploaded &&
            profilePhotoSelected
}

private fun isValidDate(date: String): Boolean {
    return try {
        val regex = Regex("""^(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.\d{4}$""")
        regex.matches(date)
    } catch (e: Exception) {
        false
    }
}

private fun isValidLicenseNumber(licenseNumber: String): Boolean {
    // Валидация: ровно 10 цифр
    return licenseNumber.matches(Regex("^\\d{10}$"))
}