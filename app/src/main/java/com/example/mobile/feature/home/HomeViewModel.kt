package com.example.mobile.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Content(val cars: List<Car>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val query = MutableStateFlow("")

    private var searchJob: Job? = null

    init { load() }

    fun load() {
        _uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            try {
                _uiState.value = HomeUiState.Content(CarsRepository.getAll())
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Не удалось загрузить автомобили")
            }
        }
    }

    fun submitSearch() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val result = CarsRepository.searchByBrand(query.value)
                _uiState.value = HomeUiState.Content(result)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Ошибка поиска")
            }
        }
    }
}
