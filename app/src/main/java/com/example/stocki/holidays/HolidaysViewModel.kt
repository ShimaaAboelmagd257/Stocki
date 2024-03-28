package com.example.stocki.holidays

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HolidaysViewModel @Inject constructor(val holidaysUseCase: HolidaysUseCase)  : ViewModel() {
    private val _state = MutableStateFlow<HolidaysStates>(HolidaysStates.Loading)
    val state: StateFlow<HolidaysStates> = _state
    fun fetchData() {
        viewModelScope.launch {
            _state.value = HolidaysStates.Loading
            try {
                val result = holidaysUseCase()
                _state.value = result
            } catch (e: Exception) {
                _state.value = HolidaysStates.Error(e.message ?: "An error occurred")
            }
        }
    }
}
