package com.example.stocki.ticker.tickerinfo

import com.example.stocki.data.pojos.Company
import com.example.stocki.data.pojos.CompanyResponse

sealed class TickerInfoState {
    object Loading : TickerInfoState()
    data class Data(val data: List<Company>?) : TickerInfoState()
    data class Logo(val data: List<Company>?) : TickerInfoState()

    data class Error(val error: String) : TickerInfoState()
}