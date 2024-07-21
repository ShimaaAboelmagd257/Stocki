package com.example.stocki.market.stocks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocki.ticker.tickerinfo.TickerInfoState
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

   /* fun fetchTickerLogo(ticker:List<String>) {
        viewModelScope.launch {

            _state.value = MarketState.Loading
            try {
                val logoMap = mutableMapOf<String, String>()
                ticker.forEach { ticker ->
                    val logo = marketUseCase.getTickerLogo(ticker)
                    val logoUrl = logo?.logoUrl
                        logoMap[ticker] = logoUrl?:" "

                }
                _state.value = MarketState.Logo(logoMap)
               *//* val result = marketUseCase.getAllTickerLogo(ticker)
              //  _state.value = MarketState.Logo()
               _state.value = result*//*
            } catch (e: Exception) {
                _state.value = MarketState.Error(e.message ?: "An error occurred")
            }
        }
    }*/
}
