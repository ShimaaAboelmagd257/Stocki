package com.example.stocki.ticker.tickertype
import com.example.stocki.data.pojos.TickerType

sealed class TickerTypeState {
    object Loading : TickerTypeState()
    data class Data(val data: List<TickerType>) : TickerTypeState()
    data class Error(val error: String) : TickerTypeState()
}
