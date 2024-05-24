package com.example.stocki.market.stocks

import com.example.stocki.data.pojos.AggregateData
import com.example.stocki.data.pojos.BrandingSaved

sealed class MarketState {
    object Loading : MarketState()
    data class Data(val data: List<AggregateData>?) : MarketState()
    data class Logo(val logo: Map<String, String>) : MarketState()
    data class Error(val error: String) : MarketState()
}
