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
 class SmaViewModel @Inject constructor(val smaUsecase: SmaUsecase)  : ViewModel() {


    private val _state = MutableStateFlow<SmaState>(SmaState.Loading)
    val state: StateFlow<SmaState> = _state
    fun fetchData(stockTicker: String) {
        viewModelScope.launch {
            _state.value = SmaState.Loading
            try {
                val result = smaUsecase(stockTicker)
                _state.value = result
            } catch (e: Exception) {
                _state.value = SmaState.Error(e.message ?: "An error occurred")
                Log.e("stockiSMA", "ERROR ${e.message}")

            }
        }
    }
}
@HiltViewModel
class EmaViewModel @Inject constructor(val emaUsecase: EmaUsecase)  : ViewModel() {


    private val _state = MutableStateFlow<EmaState>(EmaState.Loading)
    val state: StateFlow<EmaState> = _state
    fun fetchData(stockTicker: String ) {
        viewModelScope.launch {
            _state.value = EmaState.Loading
            try {
                val result = emaUsecase(stockTicker)
                _state.value = result
            } catch (e: Exception) {
                _state.value = EmaState.Error(e.message ?: "An error occurred")
                Log.e("stockiSMA", "ERROR ${e.message}")

            }
        }
    }
}
@HiltViewModel
class MacdViewModel @Inject constructor(val macdUsecase: MacdUsecase)  : ViewModel() {


    private val _state = MutableStateFlow<MacdState>(MacdState.Loading)
    val state: StateFlow<MacdState> = _state
    fun fetchData(stockTicker: String) {
        viewModelScope.launch {
            _state.value = MacdState.Loading
            try {
                val result = macdUsecase(stockTicker)
                _state.value = result
            } catch (e: Exception) {
                _state.value = MacdState.Error(e.message ?: "An error occurred")
                Log.e("stockiSMA", "ERROR ${e.message}")

            }
        }
    }
}
@HiltViewModel
class RsiViewModel @Inject constructor(val rsiUsecase: RsiUsecase)  : ViewModel() {


    private val _state = MutableStateFlow<RsiState>(RsiState.Loading)
    val state: StateFlow<RsiState> = _state
    fun fetchData(stockTicker: String ) {
        viewModelScope.launch {
            _state.value = RsiState.Loading
            try {
                val result = rsiUsecase(stockTicker)
                _state.value = result
            } catch (e: Exception) {
                _state.value = RsiState.Error(e.message ?: "An error occurred")
                Log.e("stockiSMA", "ERROR ${e.message}")

            }
        }
    }
}

