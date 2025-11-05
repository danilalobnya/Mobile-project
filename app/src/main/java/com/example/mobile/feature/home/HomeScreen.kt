package com.example.mobile.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToSettings: () -> Unit = {},
    vm: HomeViewModel = viewModel()
) {
    val state by vm.uiState.collectAsState()
    val query by vm.query.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onNavigateToHome = { /* уже на home */ },
                onNavigateToSettings = onNavigateToSettings
            )
        }
    ) { padding ->
        when (val s = state) {
            is HomeUiState.Loading -> Loader(padding)
            is HomeUiState.Error -> ErrorBlock(message = s.message, onRetry = vm::load, padding = padding)
            is HomeUiState.Content -> Content(
                cars = s.cars,
                query = query,
                onQueryChange = { vm.query.value = it },
                onSearch = vm::submitSearch,
                padding = padding
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(
    onNavigateToHome: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_home_icon), contentDescription = "Главная") },
            label = { Text("Главная") },
            selected = true,
            onClick = onNavigateToHome
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_settings_icon), contentDescription = "Настройки") },
            label = { Text("Настройки") },
            selected = false,
            onClick = onNavigateToSettings
        )
    }
}

@Composable
private fun Loader(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(R.drawable.iris), contentDescription = null, modifier = Modifier.size(120.dp))
        Spacer(Modifier.height(12.dp))
        Text("Ищем подходящие автомобили")
        Spacer(Modifier.height(8.dp))
        CircularProgressIndicator(strokeWidth = 2.dp)
    }
}

@Composable
private fun ErrorBlock(message: String, onRetry: () -> Unit, padding: PaddingValues) {
    Column(
        modifier = Modifier.fillMaxSize().padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message, color = MaterialTheme.colorScheme.error)
        Spacer(Modifier.height(12.dp))
        OutlinedButton(onClick = onRetry) { Text("Повторить") }
    }
}

@Composable
private fun Content(
    cars: List<Car>,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    padding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Поисковая строка
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                placeholder = { Text("Введите марку автомобиля") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                trailingIcon = {
                    TextButton(onClick = onSearch) { Text("Найти") }
                }
            )
            Spacer(Modifier.height(16.dp))

            Text(
                "Давайте найдём автомобиль",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }

        items(cars, key = { it.id }) { car ->
            CarCard(
                car = car,
                onBook = { /* TODO: навигация к бронированию */ },
                onDetails = { /* TODO: детали авто */ }
            )
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
private fun CarCard(
    car: Car,
    onBook: () -> Unit,
    onDetails: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        // заголовки
        Text(
            text = car.title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
        Text(
            text = car.brand,
            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF7C7B80))
        )

        Spacer(Modifier.height(8.dp))

        // картинка справа (в макете справа — для простоты поместим сверху/справа)
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                val price = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append("${car.pricePerDay}₽") }
                    append(" в день")
                }
                Text(price, style = MaterialTheme.typography.bodyLarge)

                Spacer(Modifier.height(6.dp))
                Text("${car.transmission}   ${car.fuel}", color = Color(0xFF7C7B80))
            }
            Spacer(Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.iris),
                contentDescription = null,
                modifier = Modifier.size(130.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        // кнопки
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = onBook,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A1246))
            ) {
                Text("Забронировать")
            }
            OutlinedButton(
                onClick = onDetails,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Детали")
            }
        }
    }
}
