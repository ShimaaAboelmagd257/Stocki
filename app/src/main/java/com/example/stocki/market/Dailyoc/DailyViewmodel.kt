package com.example.stocki.market.Dailyoc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewmodel @Inject constructor(val dailyUsecase: DailyUsecase)  : ViewModel() {
    private val _state = MutableStateFlow<DailyState>(DailyState.Loading)
    val state: StateFlow<DailyState> = _state
    fun fetchData(ticker:String, date: String) {
        viewModelScope.launch {
            _state.value = DailyState.Loading
            try {
                val result = dailyUsecase(ticker,date)
                _state.value = result
            } catch (e: Exception) {
                _state.value = DailyState.Error(e.message ?: "An error occurred")
            }
        }
    }
}
