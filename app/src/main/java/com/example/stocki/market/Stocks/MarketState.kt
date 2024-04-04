package com.example.stocki.market.Stocks

import com.example.stocki.data.pojos.AggregateData
sealed class MarketState {
    object Loading : MarketState()
    data class Data(val data: List<AggregateData>?) : MarketState()
    data class Error(val error: String) : MarketState()
}
