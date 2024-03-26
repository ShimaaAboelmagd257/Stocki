package com.example.stocki.search

import com.example.stocki.data.pojos.Ticker
import com.example.stocki.data.pojos.TickerTypes

sealed class SearchState {
    object Loading : SearchState()
    data class Data(val data: List<TickerTypes>?) : SearchState()
    data class Error(val error: String) : SearchState()
}