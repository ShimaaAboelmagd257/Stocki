package com.example.stocki.market.Dailyoc

import com.example.stocki.data.pojos.AggregateData
import com.example.stocki.data.pojos.DailyOpenCloseResponse

sealed class DailyState {
    object Loading : DailyState()
    data class Data(val data: DailyOpenCloseResponse) : DailyState()
    data class Error(val error: String) : DailyState()
}
