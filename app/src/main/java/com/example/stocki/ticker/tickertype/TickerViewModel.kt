package com.example.stocki.ticker.tickertype

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TickerViewModel @Inject constructor(val tickerTypeUseCae: TickerTypeUseCae)  : ViewModel() {

    private val _state = MutableStateFlow<TickerTypeState>(TickerTypeState.Loading)
    val state: StateFlow<TickerTypeState> = _state

    fun fetchData(ticker: String , locale :String) {
        viewModelScope.launch {
            _state.value = TickerTypeState.Loading
            try {
                val result = tickerTypeUseCae(ticker,locale)
                _state.value = result
            } catch (e: Exception) {
                _state.value = TickerTypeState.Error(e.message ?: "An error occurred")
            }
        }
    }
}
