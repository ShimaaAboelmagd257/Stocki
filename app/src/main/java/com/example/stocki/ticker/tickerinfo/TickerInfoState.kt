package com.example.stocki.ticker.tickerinfo

import com.example.stocki.data.pojos.AggregateData
import com.example.stocki.data.pojos.Company
import com.example.stocki.data.pojos.CompanyResponse

sealed class TickerInfoState {
    object Loading : TickerInfoState()
  //  data class Data(val data: List<Company>?) : TickerInfoState()
  //  data class Aggriigate(val data: AggregateData) : TickerInfoState()
    data class CombinedData(val companies:  List<Company>, val aggregateData: AggregateData) : TickerInfoState()
    data class Error(val error: String) : TickerInfoState()
}
sealed class TradeState {
    object Idle : TradeState()
    object Loading : TradeState()
    object Success : TradeState()
    data class Error(val message: String) : TradeState()
}