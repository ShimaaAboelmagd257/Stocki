package com.example.stocki.ticker.tickerinfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TickerInfoViewModel @Inject constructor(val tickerInfoUseCase: TickerInfoUseCase)  : ViewModel() {

    private val _state = MutableStateFlow<TickerInfoState>(TickerInfoState.Loading)
    val state: StateFlow<TickerInfoState> = _state
    fun fetchData(ticker: String ) {
        viewModelScope.launch {
            Log.d("StockiTickerInfoViewModel", "fetchData ${ticker} ${_state.value}")

            _state.value = TickerInfoState.Loading
            try {
                val result = tickerInfoUseCase(ticker)
                _state.value = result
                Log.d("StockiTickerInfoViewModel", "fetchData ${ticker} ${_state.value}")


            } catch (e: Exception) {
                Log.d("StockiTickerInfoViewModel", "fetchData ${ticker} ${_state.value}")

                _state.value = TickerInfoState.Error(e.message ?: "An error occurred")
            }
        }
    }
}
