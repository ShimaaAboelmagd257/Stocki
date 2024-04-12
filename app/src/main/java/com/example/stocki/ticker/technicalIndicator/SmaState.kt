package com.example.stocki.ticker.technicalIndicator
import com.example.stocki.data.pojos.marketData.SimpleMovingAverage

sealed class SmaState {
    object Loading : SmaState()
    data class Data(val data: SimpleMovingAverage) : SmaState()
    data class Error(val error: String) : SmaState()
}