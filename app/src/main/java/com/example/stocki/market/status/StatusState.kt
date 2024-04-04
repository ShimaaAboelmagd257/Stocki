package com.example.stocki.market.status

import com.example.stocki.data.pojos.MarketStatusResponse

sealed class StatusState {
    object Loading : StatusState()
    data class Data(val data: MarketStatusResponse) : StatusState()
    data class Error(val error: String) : StatusState()
}
