package com.example.stocki.market.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocki.holidays.HolidaysStates
import com.example.stocki.holidays.HolidaysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatusViewmodel @Inject constructor(val statusUseCase: StatusUseCase)  : ViewModel() {
    private val _state = MutableStateFlow<StatusState>(StatusState.Loading)
    val state: StateFlow<StatusState> = _state
    fun fetchData() {
        viewModelScope.launch {
            _state.value = StatusState.Loading
            try {
                val result = statusUseCase()
                _state.value = result
            } catch (e: Exception) {
                _state.value = StatusState.Error(e.message ?: "An error occurred")
            }
        }
    }
}
