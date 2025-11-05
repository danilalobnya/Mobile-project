package com.example.mobile.feature.profile

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.example.mobile.feature.register.createImageUri
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val ctx = LocalContext.current
    val vm: ProfileViewModel = viewModel(factory = ProfileViewModel.factory(AppPreferences(ctx)))
    val scope = rememberCoroutineScope()

    // collectAsState с initial
    val nameFlow by vm.userName.collectAsState(initial = "")
    val emailFlow by vm.userEmail.collectAsState(initial = "")
    val avatarFlow by vm.userAvatar.collectAsState(initial = "")
    var avatarLocal by remember { mutableStateOf("") }


    var name by remember(nameFlow) { mutableStateOf(nameFlow) }
    var email by remember(emailFlow) { mutableStateOf(emailFlow) }

    // pickers
    val galleryPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        val s = uri?.toString().orEmpty()
        avatarLocal = s                       // ← мгновенно в UI
        scope.launch { vm.setAvatar(s.ifEmpty { null }) }
    }

    var pendingUri by remember { mutableStateOf<android.net.Uri?>(null) }
    val takePicture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            val s = pendingUri?.toString().orEmpty()
            avatarLocal = s                   // ← мгновенно в UI
            scope.launch { vm.setAvatar(s.ifEmpty { null }) }
        }
        pendingUri = null
    }

    val requestCamera = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            val uri = createImageUri(ctx).also { pendingUri = it }
            takePicture.launch(uri)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Профиль") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Box(
                    Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF5F5F5))
                        .border(2.dp, Color(0xFFE0E0E0), CircleShape)
                ) {
                    val model = avatarLocal.ifEmpty { avatarFlow }
                    if (model.isNotEmpty()) {
                        AsyncImage(
                            model = model,
                            contentDescription = null,
                            modifier = Modifier
                                .matchParentSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.profile_image),
                            contentDescription = null,
                            modifier = Modifier
                                .matchParentSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                OutlinedButton(onClick = {
                    galleryPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) { Text("Галерея") }
                OutlinedButton(onClick = { requestCamera.launch(Manifest.permission.CAMERA) }) { Text("Камера") }
            }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Имя") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))
            Button(onClick = { scope.launch { vm.saveProfile(name, email) } }, modifier = Modifier.fillMaxWidth()) {
                Text("Сохранить", fontWeight = FontWeight.Medium)
            }
            
            Spacer(Modifier.weight(1f))
            
            // Кнопка выхода из профиля
            OutlinedButton(
                onClick = { 
                    scope.launch { vm.logout() }
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Выйти из профиля", fontWeight = FontWeight.Medium)
            }
        }
    }
}

class ProfileViewModel(private val prefs: AppPreferences) : ViewModel() {
    val userName = prefs.userNameFlow
    val userEmail = prefs.userEmailFlow
    val userAvatar = prefs.userAvatarUriFlow

    suspend fun saveProfile(name: String, email: String) = prefs.setUserProfile(name, email)
    suspend fun setAvatar(uri: String?) = prefs.setUserAvatarUri(uri)
    suspend fun logout() = prefs.logout()

    companion object {
        fun factory(prefs: AppPreferences) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                ProfileViewModel(prefs) as T
        }
    }
}
