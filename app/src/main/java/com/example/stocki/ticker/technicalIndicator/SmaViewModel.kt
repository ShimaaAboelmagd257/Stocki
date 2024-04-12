package com.example.stocki.ticker.technicalIndicator

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocki.ticker.tickerinfo.TickerInfoState
import com.example.stocki.ticker.tickerinfo.TickerInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SmaViewModel @Inject constructor(val smaUsecase: SmaUsecase)  : ViewModel() {


    private val _state = MutableStateFlow<SmaState>(SmaState.Loading)
    val state: StateFlow<SmaState> = _state
    fun fetchData(stockTicker: String /*, timeSpan:String */) {
        viewModelScope.launch {
            _state.value = SmaState.Loading
            try {
                val result = smaUsecase(stockTicker/*,timeSpan*/)
                _state.value = result
            } catch (e: Exception) {
                _state.value = SmaState.Error(e.message ?: "An error occurred")
                Log.e("stockiSMA", "ERROR ${e.message}")

            }
        }
    }
}
