package com.example.stocki.market.stocksplits

import com.example.stocki.data.pojos.StockSplit

sealed class SplitsState {
    object Loading : SplitsState()
    data class Data(val data: List<StockSplit>) : SplitsState()
    data class Error(val error: String) : SplitsState()

}