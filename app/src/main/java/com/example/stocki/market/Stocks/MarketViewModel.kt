package com.example.stocki.market.Stocks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor( val marketUseCase: MarketUseCase)  : ViewModel() {
    private val _state = MutableStateFlow<MarketState>(MarketState.Loading)
    val state: StateFlow<MarketState> = _state
    fun fetchData(date: String) {
        viewModelScope.launch {
            _state.value = MarketState.Loading
            try {
                val result = marketUseCase(date)
                _state.value = result
            } catch (e: Exception) {
                _state.value = MarketState.Error(e.message ?: "An error occurred")
            }
        }
    }
}
